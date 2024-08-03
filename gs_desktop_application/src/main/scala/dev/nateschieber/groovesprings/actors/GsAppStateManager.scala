package dev.nateschieber.groovesprings.actors

import akka.Done
import akka.actor.CoordinatedShutdown
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.receptionist.ServiceKey
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.stream.impl
import dev.nateschieber.groovesprings.actors.GsAppStateManager.appState
import dev.nateschieber.groovesprings.entities.{AppState, AppStateJsonSupport, EmptyAppState, EmptyPlaylist, EmptyTrack, Playlist, Track}
import dev.nateschieber.groovesprings.enums.GsPlayState
import dev.nateschieber.groovesprings.enums.GsPlayState.PLAY
import dev.nateschieber.groovesprings.rest.{CacheStateDto, CacheStateJsonSupport, FileSelectDto, FileSelectJsonSupport, PlaybackSpeedDto, PlaybackSpeedJsonSupport}
import dev.nateschieber.groovesprings.traits.{AddTrackToPlaylist, ClearPlaylist, CurrPlaylistTrackIdx, GsCommand, HydrateState, HydrateStateToDisplay, InitialTrackSelect, NextTrack, PauseTrig, PlayFromTrackSelectTrig, PlayTrig, PrevTrack, RespondAddTrackToPlaylist, RespondCurrPlaylistTrackIdx, RespondHydrateState, RespondSetPlaylist, RespondTrackSelect, SetPlaybackSpeed, SetPlaylist, StopTrig, TrackSelect, TransportTrig}

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
    AppState(appState.playState, appState.playbackSpeed, appState.currTrack, appState.currPlaylistTrackIdx, appState.playlist)
  }
  
  private def setAppState(newState: AppState): Unit = {
    appState = AppState(newState.playState, newState.playbackSpeed, newState.currTrack, newState.currPlaylistTrackIdx, newState.playlist)
  }

  def setCurrTrack(appState: AppState, track: Track): AppState = {
    // TODO:
    //   - maybe update playState here
    AppState(appState.playState, appState.playbackSpeed, track, appState.currPlaylistTrackIdx, appState.playlist)
  }

  def addTrackToPlaylist(appState: AppState, track: Track): AppState = {
    AppState(
      appState.playState,
      appState.playbackSpeed,
      appState.currTrack,
      appState.currPlaylistTrackIdx,
      Playlist(appState.playlist.id, appState.playlist.name, appState.playlist.tracks ++ List(track))
    )
  }

  private def setPlaylist(appState: AppState, playlist: Playlist): AppState = {
    AppState(appState.playState, appState.playbackSpeed, appState.currTrack, 0, playlist)
  }
  
  private def clearPlaylist(appState: AppState): AppState = {
    AppState(appState.playState, appState.playbackSpeed, appState.currTrack, appState.currPlaylistTrackIdx, EmptyPlaylist)
  }

  private def prevTrack(appState: AppState): AppState = {
    val playlistLength = appState.playlist.tracks.length
    if (playlistLength == 0)
      return AppState(appState.playState, appState.playbackSpeed, appState.currTrack, 0, EmptyPlaylist)

    val oldIdx = appState.currPlaylistTrackIdx
    val newIdx = (oldIdx + (playlistLength - 1)) % playlistLength

    AppState(
      appState.playState,
      appState.playbackSpeed,
      appState.playlist.tracks(newIdx),
      newIdx,
      appState.playlist
    )
  }

  private def nextTrack(appState: AppState): AppState = {
    val playlistLength = appState.playlist.tracks.length
    if (playlistLength == 0)
      return AppState(
        appState.playState,
        appState.playbackSpeed,
        appState.currTrack,
        0,
        EmptyPlaylist
      )

    val oldIdx = appState.currPlaylistTrackIdx
    val newIdx = (oldIdx + 1) % playlistLength

    AppState(
      appState.playState,
      appState.playbackSpeed,
      appState.playlist.tracks(newIdx),
      newIdx,
      appState.playlist
    )
  }

  private def setPlayState(appState: AppState, newPlayState: GsPlayState): AppState = {
    AppState(
      newPlayState,
      appState.playbackSpeed,
      appState.currTrack,
      appState.currPlaylistTrackIdx,
      appState.playlist
    )
  }

  private def setCurrPlaylistTrackIdx(appState: AppState, newIdx: Int): AppState = {
    var optTrack = appState.playlist.tracks.lift(newIdx)
    if (optTrack.isDefined)
      AppState(
        appState.playState,
        appState.playbackSpeed,
        optTrack.get, 
        newIdx, 
        appState.playlist)
    else
      AppState(
        appState.playState,
        appState.playbackSpeed,
        EmptyTrack,
        0, 
        appState.playlist
      )
  }

  private def hydrateState(): Unit = {
    gsDisplayRef ! HydrateState(appState.toJson.compactPrint, context.self)
  }

  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    msg match {
      case TrackSelect(track, replyTo) =>
        appState = setCurrTrack(appState, track)

        // TODO: un-overload TrackSelect
        gsPlaybackRef ! TrackSelect(track, context.self)
        hydrateState()
        replyTo ! RespondTrackSelect(track.path, context.self)
        Behaviors.same

      case RespondTrackSelect(path, _replyTo) =>
        // auto play on TrackSelect
        gsPlaybackRef ! PlayFromTrackSelectTrig(path, gsDisplayRef)
        Behaviors.same

      case HydrateStateToDisplay() =>
        hydrateState()
        Behaviors.same

      case AddTrackToPlaylist(track, replyTo) =>
        appState = addTrackToPlaylist(appState, track)
        hydrateState()
        replyTo ! RespondAddTrackToPlaylist(context.self)
        Behaviors.same

      case SetPlaylist(playlist, replyTo) =>
        appState = setPlaylist(appState, playlist)
        hydrateState()
        replyTo ! RespondSetPlaylist(context.self)
        Behaviors.same

      case ClearPlaylist(replyTo) =>
        appState = clearPlaylist(appState)
        hydrateState()
        Behaviors.same

      case CurrPlaylistTrackIdx(newIdx, replyTo) =>
        appState = setCurrPlaylistTrackIdx(appState, newIdx)
        gsPlaybackRef ! TrackSelect(appState.currTrack, context.self)
        hydrateState()
        replyTo ! RespondCurrPlaylistTrackIdx(context.self)
        Behaviors.same

      case TransportTrig(newPlayState) =>
        appState = setPlayState(appState, newPlayState)
        newPlayState match {
          case GsPlayState.PLAY => gsPlaybackRef ! PlayTrig(gsDisplayRef)
          case GsPlayState.PAUSE => gsPlaybackRef ! PauseTrig(gsDisplayRef)
          case default => gsPlaybackRef ! StopTrig(gsDisplayRef)
        }
        hydrateState()
        Behaviors.same

      case PrevTrack() =>
        appState = prevTrack(appState)
        gsPlaybackRef ! TrackSelect(appState.currTrack, context.self)
        hydrateState()
        Behaviors.same

      case NextTrack() =>
        appState = nextTrack(appState)
        gsPlaybackRef ! TrackSelect(appState.currTrack, context.self)
        hydrateState()
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
