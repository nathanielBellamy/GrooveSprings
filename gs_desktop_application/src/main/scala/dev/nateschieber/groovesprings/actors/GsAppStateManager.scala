package dev.nateschieber.groovesprings.actors

import akka.Done
import akka.actor.CoordinatedShutdown
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.receptionist.ServiceKey
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import dev.nateschieber.groovesprings.actors.GsAppStateManager.appState
import dev.nateschieber.groovesprings.entities.{AppState, AppStateJsonSupport, EmptyAppState, EmptyPlaylist, EmptyTrack, Playlist, Track}
import dev.nateschieber.groovesprings.enums.GsAppStateManagerTimer.currFrameIdCache
import dev.nateschieber.groovesprings.enums.GsLoopType.{ALL, ONE}
import dev.nateschieber.groovesprings.enums.{GsAppStateManagerTimer, GsLoopType, GsPlayState, GsPlaybackSpeed}
import dev.nateschieber.groovesprings.enums.GsPlayState.{PAUSE, PLAY, STOP}
import dev.nateschieber.groovesprings.rest.FileSelectJsonSupport
import dev.nateschieber.groovesprings.traits.{AddTrackToPlaylist, ClearPlaylist, CurrPlaylistTrackIdx, GsCommand, HydrateState, HydrateStateToDisplay, InitialTrackSelect, NextOrPrevTrack, NextTrack, PauseTrig, PlayFromNextOrPrevTrack, PlayFromTrackSelectTrig, PlayTrig, PrevTrack, ReadPlaybackThreadState, RespondAddTrackToPlaylist, RespondCurrPlaylistTrackIdx, RespondHydrateState, RespondNextOrPrevTrack, RespondPauseTrig, RespondPlayFromNextOrPrevTrack, RespondPlayFromTrackSelectTrig, RespondPlayTrig, RespondPlaybackThreadState, RespondRestTrackSelect, RespondSetPlaylist, RespondStopTrig, RespondTimerStart, RespondTrackSelect, RestTrackSelect, SendLastFrameId, SendReadComplete, SetLoopType, SetPlaybackSpeed, SetPlaylist, StopTrig, TimerStart, TrackSelect, TransportTrig}

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths}
import java.util.UUID
import scala.annotation.static
import scala.concurrent.Future
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
    val appState = appStateJson.parseJson.convertTo[AppState]
    AppState(
      GsPlayState.STOP,
      appState.playbackSpeed,
      appState.loopType,
      0, // currFrameId
      appState.currTrack,
      appState.currPlaylistTrackIdx,
      appState.playlist
    )
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

  private var currFrameIdCacheTimerRef: ActorRef[GsCommand] = context.spawn(
    GsTimer(GsAppStateManagerTimer.currFrameIdCache.id),
    UUID.randomUUID().toString
  )

  private var playbackThreadPollTimerRef: ActorRef[GsCommand] = context.spawn(
    GsTimer(GsAppStateManagerTimer.playbackThreadPoll.id),
    UUID.randomUUID().toString
  )

  def printState(): Unit = {
    println(appState.toJson.prettyPrint)
  }

  private def getAppState(): AppState = {
    AppState(
      appState.playState,
      appState.playbackSpeed,
      appState.loopType,
      appState.currFrameId,
      appState.currTrack,
      appState.currPlaylistTrackIdx,
      appState.playlist
    )
  }

  private def setAppState(newState: AppState): Unit = {
    appState = AppState(
      newState.playState,
      newState.playbackSpeed,
      newState.loopType,
      newState.currFrameId,
      newState.currTrack,
      newState.currPlaylistTrackIdx,
      newState.playlist
    )
  }

  def setCurrTrack(appState: AppState, track: Track): AppState = {
    // TODO:
    //   - maybe update playState here
    AppState(
      appState.playState,
      appState.playbackSpeed,
      appState.loopType,
      appState.currFrameId,
      track,
      appState.currPlaylistTrackIdx,
      appState.playlist
    )
  }

  def addTrackToPlaylist(appState: AppState, track: Track): AppState = {
    AppState(
      appState.playState,
      appState.playbackSpeed,
      appState.loopType,
      appState.currFrameId,
      appState.currTrack,
      appState.currPlaylistTrackIdx,
      Playlist(appState.playlist.id, appState.playlist.name, appState.playlist.tracks ++ List(track))
    )
  }

  private def setPlaylist(appState: AppState, playlist: Playlist): AppState = {
    AppState(
      appState.playState,
      appState.playbackSpeed,
      appState.loopType,
      appState.currFrameId,
      appState.currTrack,
      0,
      playlist
    )
  }

  private def clearPlaylist(appState: AppState): AppState = {
    AppState(
      appState.playState,
      appState.playbackSpeed,
      appState.loopType,
      appState.currFrameId,
      appState.currTrack,
      appState.currPlaylistTrackIdx,
      EmptyPlaylist
    )
  }

  // TODO:
  //   - set new currFrameId based on playbackSpeed when moving between tracks
  private def prevTrack(appState: AppState): AppState = {
    if (!(appState.playlist.tracks.map(track => track.id) contains appState.currTrack.id))
      return appState

    val playlistLength = appState.playlist.tracks.length
    if (playlistLength == 0)
      return AppState(
        appState.playState,
        appState.playbackSpeed,
        appState.loopType,
        0,
        appState.currTrack,
        0,
        EmptyPlaylist
      )

    val oldIdx = appState.currPlaylistTrackIdx
    val newIdx = (oldIdx + (playlistLength - 1)) % playlistLength

    AppState(
      appState.playState,
      appState.playbackSpeed,
      appState.loopType,
      0,
      appState.playlist.tracks(newIdx),
      newIdx,
      appState.playlist
    )
  }

  private def nextTrack(appState: AppState): AppState = {
    if (!(appState.playlist.tracks.map(track => track.id) contains appState.currTrack.id))
      return appState

    val playlistLength = appState.playlist.tracks.length
    if (playlistLength == 0)
      return AppState(
        appState.playState,
        appState.playbackSpeed,
        appState.loopType,
        0,
        appState.currTrack,
        0,
        EmptyPlaylist
      )

    val oldIdx = appState.currPlaylistTrackIdx
    val newIdx = (oldIdx + 1) % playlistLength

    AppState(
      appState.playState,
      appState.playbackSpeed,
      appState.loopType,
      0,
      appState.playlist.tracks(newIdx),
      newIdx,
      appState.playlist
    )
  }

  private def setPlayState(appState: AppState, newPlayState: GsPlayState): AppState = {
    AppState(
      newPlayState,
      appState.playbackSpeed,
      appState.loopType,
      appState.currFrameId,
      appState.currTrack,
      appState.currPlaylistTrackIdx,
      appState.playlist
    )
  }

  private def setPlaybackSpeed(state: AppState, newPlaybackSpeed: GsPlaybackSpeed): AppState = {
    AppState(
      appState.playState,
      newPlaybackSpeed,
      appState.loopType,
      appState.currFrameId,
      appState.currTrack,
      appState.currPlaylistTrackIdx,
      appState.playlist
    )
  }

  private def setLoopType(state: AppState): AppState = {
    val newLoopType = appState.loopType match {
      case GsLoopType.ONE  => GsLoopType.NONE
      case GsLoopType.ALL  => GsLoopType.ONE
      case GsLoopType.NONE => GsLoopType.ALL
    }

    AppState(
      appState.playState,
      appState.playbackSpeed,
      newLoopType,
      appState.currFrameId,
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
        appState.loopType,
        appState.currFrameId,
        optTrack.get,
        newIdx,
        appState.playlist)
    else
      AppState(
        appState.playState,
        appState.playbackSpeed,
        appState.loopType,
        appState.currFrameId,
        EmptyTrack,
        0,
        appState.playlist
      )
  }

  private def setCurrFrameId(newCurrFrameId: Long): Unit = {
    setAppState(
      AppState(
        appState.playState,
        appState.playbackSpeed,
        appState.loopType,
        newCurrFrameId,
        appState.currTrack,
        appState.currPlaylistTrackIdx,
        appState.playlist
      )
    )
  }

  private def cacheCurrFrameId(): Unit = {
    val currFrameId = if (appState.playState == GsPlayState.STOP) 0L else GsPlaybackThread.getCurrFrameId.asInstanceOf[Long]
    setAppState(
      AppState(
        appState.playState,
        appState.playbackSpeed,
        appState.loopType,
        currFrameId,
        appState.currTrack,
        appState.currPlaylistTrackIdx,
        appState.playlist
      )
    )
  }

  private def hydrateState(): Unit = {
    gsDisplayRef ! HydrateState(appState.toJson.compactPrint, context.self)
  }

  private def currFrameIdCacheTimerStart(): Unit = {
    currFrameIdCacheTimerRef ! TimerStart(500, context.self)
  }

  private def playbackThreadPollTimerStart(): Unit = {
    playbackThreadPollTimerRef ! TimerStart(50, context.self)
  }

  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    msg match {
      case RestTrackSelect(track, replyTo) =>
        setAppState(
          setCurrTrack(appState, track)
        )
        gsPlaybackRef ! TrackSelect(track, context.self)
        hydrateState()
        replyTo ! RespondRestTrackSelect(track.path, context.self)
        Behaviors.same

      case RespondTrackSelect(path, _replyTo) =>
        setAppState(
          setPlayState(appState, GsPlayState.PLAY)
        )
        gsPlaybackRef ! PlayFromTrackSelectTrig(path, context.self)
        hydrateState()
        Behaviors.same

      case RespondNextOrPrevTrack(path, _replyTo) =>
        if (appState.playState == GsPlayState.PLAY)
          gsPlaybackRef ! PlayFromNextOrPrevTrack(path, context.self)
          hydrateState()
        Behaviors.same

      case RespondPlayFromTrackSelectTrig(replyTo) =>
        replyTo ! ReadPlaybackThreadState(context.self)
        Behaviors.same

      case RespondPlayFromNextOrPrevTrack(replyTo) =>
        if (appState.playState == GsPlayState.PLAY)
          replyTo ! ReadPlaybackThreadState(context.self)
        Behaviors.same

      case HydrateStateToDisplay() =>
        hydrateState()
        Behaviors.same

      case AddTrackToPlaylist(track, replyTo) =>
        setAppState(
          addTrackToPlaylist(appState, track)
        )
        hydrateState()
        replyTo ! RespondAddTrackToPlaylist(context.self)
        Behaviors.same

      case SetPlaylist(playlist, replyTo) =>
        setAppState(
          setPlaylist(appState, playlist)
        )
        hydrateState()
        replyTo ! RespondSetPlaylist(context.self)
        Behaviors.same

      case ClearPlaylist(replyTo) =>
        setAppState(
          clearPlaylist(appState)
        )
        hydrateState()
        Behaviors.same

      case CurrPlaylistTrackIdx(newIdx, replyTo) =>
        setAppState(
          setCurrPlaylistTrackIdx(appState, newIdx)
        )
        gsPlaybackRef ! TrackSelect(appState.currTrack, context.self)
        hydrateState()
        replyTo ! RespondCurrPlaylistTrackIdx(context.self)
        Behaviors.same

      case TransportTrig(newPlayState) =>
        newPlayState match {
          case GsPlayState.PLAY =>
            gsPlaybackRef ! PlayTrig(context.self)
            currFrameIdCacheTimerStart()
          case GsPlayState.PAUSE =>
            gsPlaybackRef ! PauseTrig(context.self)
          case default =>
            gsPlaybackRef ! StopTrig(context.self)
        }
        Behaviors.same

      case RespondPlayTrig(replyTo) =>
        setAppState(
          setPlayState(appState, GsPlayState.PLAY)
        )
        replyTo ! ReadPlaybackThreadState(context.self)
        hydrateState()
        Behaviors.same

      case RespondPlaybackThreadState(lastFrameId, playState, readComplete, replyTo) =>
        if (readComplete)
          gsDisplayRef ! SendReadComplete()
          // TODO:
          //    - create playbackOption vars (loopAll | loopOne, shuffle)
          //    - let user set those vars
          //    - condition on those vars
          if (appState.playState == GsPlayState.PLAY)
            if (appState.playbackSpeed.value > 0)
              setAppState(
                nextTrack(appState)
              )
            else if (appState.playbackSpeed.value < 0)
              // TODO: start from end of track
              setAppState(
                prevTrack(appState)
              )

            gsPlaybackRef ! NextOrPrevTrack(appState.currTrack, context.self)
          return Behaviors.same

        gsDisplayRef ! SendLastFrameId(lastFrameId)
        if (playState == GsPlayState.PLAY)
          // TODO:
          //   - compare lastFrameId to currTrack.sf_info.frames
          //   - allow user to set fade-out
          playbackThreadPollTimerStart()
        Behaviors.same

      case RespondPauseTrig(replyTo) =>
        setAppState(
          setPlayState(appState, GsPlayState.PAUSE)
        )
        hydrateState()
        Behaviors.same

      case RespondStopTrig(replyTo) =>
        setAppState(
          setPlayState(appState, GsPlayState.STOP)
        )
        hydrateState()
        Behaviors.same

      case SetPlaybackSpeed(newPlaybackSpeed, _) =>
        setAppState(
          setPlaybackSpeed(appState, newPlaybackSpeed)
        )
        gsPlaybackRef ! SetPlaybackSpeed(newPlaybackSpeed, gsDisplayRef)
        hydrateState()
        Behaviors.same

      case SetLoopType(replyTo) =>
        setAppState(
          setLoopType(appState)
        )
        hydrateState()
        Behaviors.same

      case PrevTrack() =>
        setAppState(
          prevTrack(appState)
        )
        gsPlaybackRef ! NextOrPrevTrack(appState.currTrack, context.self)
        hydrateState()
        Behaviors.same

      case NextTrack() =>
        setAppState(
          nextTrack(appState)
        )
        gsPlaybackRef ! NextOrPrevTrack(appState.currTrack, context.self)
        hydrateState()
        Behaviors.same

      case RespondTimerStart(timerId, replyTo) =>
        val timer: GsAppStateManagerTimer = GsAppStateManagerTimer.fromId(timerId)
        timer match {
          case GsAppStateManagerTimer.currFrameIdCache =>
            cacheCurrFrameId()
            if (appState.playState == GsPlayState.PLAY)
              currFrameIdCacheTimerStart()
            else if (appState.playState == GsPlayState.STOP)
            setCurrFrameId(0)

          case GsAppStateManagerTimer.playbackThreadPoll =>
            gsPlaybackRef ! ReadPlaybackThreadState(context.self)

          case default =>
            println("GsAppStateManager :: Unrecognized Timer")
        }
        Behaviors.same

      case RespondHydrateState(replyTo) =>
        Behaviors.same

      case default =>
        println("GsAppStateManager :: Unmatched Message Received")
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
