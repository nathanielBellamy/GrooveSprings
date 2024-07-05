package dev.nateschieber.groovesprings.actors

import akka.Done
import akka.actor.CoordinatedShutdown
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.receptionist.ServiceKey
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.stream.impl
import dev.nateschieber.groovesprings.actors.GsAppStateManager.appState
import dev.nateschieber.groovesprings.entities.{AppState, AppStateJsonSupport, EmptyAppState, EmptyPlaylist, Playlist, Track}
import dev.nateschieber.groovesprings.rest.{CacheStateDto, CacheStateJsonSupport, FileSelectDto, FileSelectJsonSupport, PlaybackSpeedDto, PlaybackSpeedJsonSupport}
import dev.nateschieber.groovesprings.traits.{AddTrackToPlaylist, ClearPlaylist, GsCommand, HydrateState, HydrateStateToDisplay, InitialTrackSelect, PauseTrig, PlayTrig, RespondAddTrackToPlaylist, RespondHydrateState, RespondTrackSelect, SetPlaybackSpeed, StopTrig, TrackSelect}

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths}
import scala.:+
import scala.annotation.static
import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.concurrent.ExecutionContext.Implicits.global

// spray + protocol import needed to call .convertTo[AppState]
import spray.json._
import dev.nateschieber.groovesprings.entities.AppStateJsonProtocol._

object GsAppStateManager {

  private val stateCacheFile: String = "__GROOVE_SPRINGS__STATE_CACHE__.json"
  private var appState: AppState = GsAppStateManager.loadAppState()

  val GsAppStateManagerServiceKey = ServiceKey[GsCommand]("gs_rest_controller")

  @static private def loadAppState(): AppState = {
    val appStatePath = Path.of(stateCacheFile)
    if (!Files.exists(appStatePath))
      return EmptyAppState
    val appStateJson = Files.readString(appStatePath, StandardCharsets.UTF_8)
    appStateJson.parseJson.convertTo[AppState]
  }

  @static private def cacheState(appState: AppState): Unit = {
    Files.write(Paths.get(stateCacheFile), appState.toJson.compactPrint.getBytes(StandardCharsets.UTF_8))
  }

  def apply(
             gsPlaybackRef: ActorRef[GsCommand],
             gsDisplayRef: ActorRef[GsCommand]
           ): Behavior[GsCommand] = Behaviors.setup {
    context =>
      given system: ActorSystem[Nothing] = context.system

      val manager = new GsAppStateManager(context, gsPlaybackRef, gsDisplayRef)
      println("==GS Startup State::")
      gsPlaybackRef ! InitialTrackSelect(manager.getAppState().currTrack)
      manager.printState()
      manager
  }
}

class GsAppStateManager(
                        context: ActorContext[GsCommand],
                        gsPlaybackRef: ActorRef[GsCommand],
                        gsDisplayRef: ActorRef[GsCommand])
  extends AbstractBehavior[GsCommand](context)
    with FileSelectJsonSupport with AppStateJsonSupport {

  def printState(): Unit = {
    println(appState.toJson.prettyPrint)
  }

  private def getAppState(): AppState = {
    AppState(appState.currTrack, appState.currPlaylistTrackIdx, appState.playlist)
  }
  
  private def setAppState(newState: AppState): Unit = {
    appState = AppState(newState.currTrack, newState.currPlaylistTrackIdx, newState.playlist)
  }

  def setCurrTrack(appState: AppState, track: Track): AppState = {
    AppState(track, appState.currPlaylistTrackIdx, appState.playlist)
  }

  def addTrackToPlaylist(appState: AppState, track: Track): AppState = {
    AppState(
      appState.currTrack,
      appState.currPlaylistTrackIdx,
      Playlist(appState.playlist.id, appState.playlist.name, appState.playlist.tracks ++ List(track))
    )
  }

  private def clearPlaylist(appState: AppState): AppState = {
    AppState(appState.currTrack, appState.currPlaylistTrackIdx, EmptyPlaylist)
  }

  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    msg match {
      case TrackSelect(track, replyTo) =>
        println("==GsAppStateManager::TrackSelect:: " + track.toJson.prettyPrint)
        appState = setCurrTrack(appState, track)

        // TODO: un-overload TrackSelect
        gsPlaybackRef ! TrackSelect(track, context.self)
        replyTo ! RespondTrackSelect(context.self)
        Behaviors.same

      case RespondTrackSelect(_) =>
        // auto play on TrackSelect
        gsPlaybackRef ! PlayTrig(context.self)
        Behaviors.same

      case HydrateStateToDisplay() =>
        gsDisplayRef ! HydrateState(appState.toJson.compactPrint, context.self)
        Behaviors.same

      case AddTrackToPlaylist(track, replyTo) =>
        appState = addTrackToPlaylist(appState, track)
        gsDisplayRef ! HydrateState(appState.toJson.compactPrint, context.self)
        replyTo ! RespondAddTrackToPlaylist(context.self)
        Behaviors.same

      case ClearPlaylist(replyTo) =>
        appState = clearPlaylist(appState)
        gsDisplayRef ! HydrateState(appState.toJson.compactPrint, context.self)
        Behaviors.same

      case RespondHydrateState(replyTo) =>
        Behaviors.same

      case default =>
        Behaviors.same
    }
  }

  CoordinatedShutdown(context.system).addTask(CoordinatedShutdown.PhaseBeforeServiceUnbind, "cacheStateOnShutdown") { () =>
    Future {
      println("==GS Shutdown State::")
      printState()
      GsAppStateManager.cacheState(appState)
      println("==GsAppStateManager Cache State Done ==")
      Done
    }
  }
}
