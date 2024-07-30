package dev.nateschieber.groovesprings.actors

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.receptionist.ServiceKey
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, StatusCode}

import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.server.Route
import dev.nateschieber.groovesprings.GsMusicLibraryScanner
import dev.nateschieber.groovesprings.entities.{Playlist, PlaylistJsonSupport, Track, TrackJsonSupport}
import dev.nateschieber.groovesprings.enums.{GsHttpPort, GsPlaybackSpeed}
import dev.nateschieber.groovesprings.jni.JniMain
import dev.nateschieber.groovesprings.rest.{CacheStateDto, CacheStateJsonSupport, GsTrackServiceResponse, PlaybackSpeedDto, PlaybackSpeedJsonSupport}
import dev.nateschieber.groovesprings.traits.{AddTrackToPlaylist, ClearPlaylist, CurrPlaylistTrackIdx, GsCommand, HydrateStateToDisplay, NextTrack, PauseTrig, PlayTrig, PrevTrack, SetPlaybackSpeed, SetPlaylist, StopTrig, TrackSelect}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*
import spray.json.*
import dev.nateschieber.groovesprings.rest.GsTrackServiceResponseProtocol.gsTrackServiceResponseFormat

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
    with PlaylistJsonSupport
    with TrackJsonSupport
    with PlaybackSpeedJsonSupport
    with CacheStateJsonSupport {

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
      path("api" / "v1" / "transport") {
        get {
          parameters(Symbol("cmd").as[String]) { (cmd: String) => {
            cmd match {
              case "play" =>
                gsPlaybackRef ! PlayTrig(gsDisplayRef)
              case "pause" =>
                gsPlaybackRef ! PauseTrig(gsDisplayRef)
              case "stop" =>
                gsPlaybackRef ! StopTrig(gsDisplayRef)
              case "prevTrack" =>
                gsAppStateManagerRef ! PrevTrack()
              case "nextTrack" =>
                gsAppStateManagerRef ! NextTrack()
            }
            complete(cmd)
          }}
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
      path("api" / "v1" / "setPlaylist") {
        put {
          entity(as[Playlist]) { playlist => {
            gsAppStateManagerRef ! SetPlaylist(playlist, context.self)
            complete("Playlist Set")
          }}
        }
      },
      pathPrefix("api" / "v1"/ "playlistCrud") {
        // 1. client performs playlist crud
        // 2. GsRestController (GsRC) proxies crud request to gs-track-service
        // 3. GsRC receives response and triggers state update + hydrate
        // 4. client receives state update through gs-display ws
        // TODO:
        //  - 5. client triggers playlists fetch on completion of http call
        extractUnmatchedPath { remaining =>
          extractMethod { method =>
            extractRequestEntity { requestEntity =>
              implicit val classic = context.system.classicSystem
              val timeout = 2000.milliseconds
              val future = Http().singleRequest(HttpRequest(
                method = method,
                uri = s"http://localhost:5173/api/v1/playlists$remaining", // gs-track-service
                entity = requestEntity
              ))
              val resultJsonString = Await.result(
                future
                  .flatMap { resp => resp.entity.toStrict(timeout) }
                  .map { strictEntity => strictEntity.data.utf8String },
                timeout
              )
              val gsTrackServiceResponse = resultJsonString.parseJson.convertTo[GsTrackServiceResponse[Playlist]]
              gsAppStateManagerRef ! SetPlaylist(gsTrackServiceResponse.data, context.self)

              complete(HttpResponse(200))
            }
          }
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
