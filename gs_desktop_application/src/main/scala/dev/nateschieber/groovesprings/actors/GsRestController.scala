package dev.nateschieber.groovesprings.actors

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.receptionist.ServiceKey
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.server.Route
import dev.nateschieber.groovesprings.GsMusicLibraryScanner
import dev.nateschieber.groovesprings.actors.GsRestController.appStateCacheFile
import dev.nateschieber.groovesprings.entities.{Track, TrackJsonSupport}
import dev.nateschieber.groovesprings.enums.{GsHttpPort, GsPlaybackSpeed}
import dev.nateschieber.groovesprings.jni.JniMain
import dev.nateschieber.groovesprings.rest.{CacheStateDto, CacheStateJsonSupport, FileSelectDto, FileSelectJsonSupport, PlaybackSpeedDto, PlaybackSpeedJsonSupport}
import dev.nateschieber.groovesprings.traits.{AddTrackToPlaylist, ClearPlaylist, CurrPlaylistTrackIdx, GsCommand, HydrateStateToDisplay, PauseTrig, PlayTrig, SetPlaybackSpeed, StopTrig, TrackSelect}

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths}
import scala.concurrent.ExecutionContext.Implicits.global

object GsRestController {

  private var appStateCacheFile: String = "__GROOVE_SPRINGS__LAST_STATE__.json"

  val GsRestControllerServiceKey = ServiceKey[GsCommand]("gs_rest_controller")

  def apply(
             gsAppStateManagerRef: ActorRef[GsCommand],
             gsPlaybackRef: ActorRef[GsCommand],
             gsDisplayRef: ActorRef[GsCommand]
           ): Behavior[GsCommand] = Behaviors.setup {
    context =>
      given system: ActorSystem[Nothing] = context.system

      val gsRestController = new GsRestController(context, gsAppStateManagerRef, gsPlaybackRef, gsDisplayRef)

      lazy val server = Http()
        .newServerAt("localhost", GsHttpPort.GsRestController.port)
        .bind(gsRestController.routes())

      server.map { _ =>
        println("GsRestControllerServer online at localhost:" + GsHttpPort.GsRestController.port)
      } recover { case ex =>
        println(ex.getMessage)
      }

      gsRestController
  }
}

class GsRestController(
                        context: ActorContext[GsCommand],
                        gsAppStateManagerRef: ActorRef[GsCommand],
                        gsPlaybackRef: ActorRef[GsCommand],
                        gsDisplayRef: ActorRef[GsCommand])
  extends AbstractBehavior[GsCommand](context)
    with TrackJsonSupport with PlaybackSpeedJsonSupport with CacheStateJsonSupport {

  def routes(): Route = {
    concat(
      path("api" / "v1" / "scan") {
        post {
          // TODO: pass in array of dirs from body
          GsMusicLibraryScanner.run()
          println("Audio File Scan Complete")
          complete("Audio File Scan Complete;")
        }
      },
      path("api" / "v1" / "hello") {
        get {
          complete("Hello World! Your friend, Akka. ")
        }
      },
      path("api" / "v1" / "add") {
        get {
          parameters(Symbol("x").as[Int], Symbol("y").as[Int]) { (x: Int, y: Int) => {
            complete("result: " + JniMain.add(x, y))
          }}
        }
      },
      path("api" / "v1" / "appState"){
        get {
          // TODO:
          //   - client initial state hydration
          //   - Client -http-> GsRestController -> GsAppStateManager -> GsDisplay -ws-> Client
          gsAppStateManagerRef ! HydrateStateToDisplay()
          complete("loading")
        }
      },
      path("api" / "v1" / "cacheState") {
        put {
          entity(as[CacheStateDto]) { dto => {
//            cacheLastState(dto.stateJson)
            complete("cached")
          }}
        }
      },
      // Transport Controls
      path("api" / "v1" / "transport" / "play") {
        get {
          gsPlaybackRef ! PlayTrig(gsDisplayRef)
          complete("play")
        }
      },
      path("api" / "v1" / "transport" / "pause") {
        get {
          gsPlaybackRef ! PauseTrig(gsDisplayRef)
          complete("pause")
        }
      },
      path("api" / "v1" / "transport" / "stop") {
        get {
          gsPlaybackRef ! StopTrig(gsDisplayRef)
          complete("stop")
        }
      },
      path("api" / "v1" / "transport" / "playbackSpeed") {
        post {
          entity(as[PlaybackSpeedDto]) { dto => {
            val speed = dto.speed
            val gsSpeed: GsPlaybackSpeed = gsPlaybackSpeedFromDouble(dto.speed)
            gsPlaybackRef ! SetPlaybackSpeed(gsSpeed, gsDisplayRef)
            complete(s"playbackSpeed: $gsSpeed")
          }}
        }
      },
      path("api" / "v1" / "trackSelect") {
        put { // update GsPlaybackThread.filePath
          entity(as[Track]) { track => {
            gsAppStateManagerRef ! TrackSelect(track, context.self)
            complete("Track Selected")
          }}
        }
      },
      path("api" / "v1" / "addTrackToPlaylist") {
        put {
          entity(as[Track]) { track => {
            gsAppStateManagerRef ! AddTrackToPlaylist(track, context.self)
            complete("Track Added To Playlist")
          }}
        }
      },
      path("api" / "v1" / "clearPlaylist") {
        delete {
          gsAppStateManagerRef ! ClearPlaylist(context.self)
          complete("Playlist Cleared")
        }
      },
      path("api" / "v1" / "currPlaylistTrackIdx") {
        put {
          parameters(Symbol("newIdx").as[Int]) { (newIdx: Int) => {
            gsAppStateManagerRef ! CurrPlaylistTrackIdx(newIdx, context.self)
            complete("Curr Playlist Track Idx")
          }}
        }
      },
      pathPrefix("api" / "v1") {
        // proxy all other api/v1 traffic to gs-track-service
        extractUnmatchedPath { remaining =>
          extractMethod { method =>
            extractRequestEntity { requestEntity =>
              implicit val classic = context.system.classicSystem
              val response = Http().singleRequest(HttpRequest(
                method = method,
                uri = s"http://localhost:5173/api/v1$remaining", // gs-track-service
                entity = requestEntity
              ))
              complete(response)
            }
          }
        }
      },
      path("") { //the same prefix must be set as base href in index.html
        getFromResource("frontend-dist/browser/index.html")
      } ~ pathPrefix("") {
        getFromResourceDirectory("frontend-dist/browser/") ~
          getFromResource("frontend-dist/browser/index.html")
      },
    )
  }

  def gsPlaybackSpeedFromDouble(speed: Double): GsPlaybackSpeed = {
    speed match {
      case -2.0    => GsPlaybackSpeed._N2
      case -1.0    => GsPlaybackSpeed._N1
      case -0.5    => GsPlaybackSpeed._N05
      case  0.5    => GsPlaybackSpeed._05
      case  2.0    => GsPlaybackSpeed._2
      case default => GsPlaybackSpeed._1 // case 1
    }
  }

  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    Behaviors.same
  }
}
