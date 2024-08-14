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
import dev.nateschieber.groovesprings.helpers.GsAppStateMutations
import dev.nateschieber.groovesprings.rest.FileSelectJsonSupport
import dev.nateschieber.groovesprings.traits.{AddTrackToPlaylist, ClearPlaylist, CurrPlaylistTrackIdx, GsCommand, HydrateState, HydrateStateToDisplay, InitialTrackSelect, NextOrPrevTrack, NextTrack, PauseTrig, PlayFromNextOrPrevTrack, PlayFromTrackSelectTrig, PlayTrig, PrevTrack, ReadPlaybackThreadState, RespondAddTrackToPlaylist, RespondCurrPlaylistTrackIdx, RespondHydrateState, RespondNextOrPrevTrack, RespondPauseTrig, RespondPlayFromNextOrPrevTrack, RespondPlayFromTrackSelectTrig, RespondPlayTrig, RespondPlaybackThreadState, RespondRestTrackSelect, RespondSetPlaylist, RespondStopTrig, RespondTimerStart, RespondTrackSelect, RestTrackSelect, SendLastFrameId, SendReadComplete, SetFilePath, SetLoopType, SetPlaybackSpeed, SetPlaylist, SetShuffle, StopTrig, TimerStart, TrackSelect, TransportTrig}

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

  val GsAppStateManagerServiceKey: ServiceKey[GsCommand] = ServiceKey[GsCommand]("gs_rest_controller")

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
      appState.shuffle,
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

  private val stateMutation: GsAppStateMutations = GsAppStateMutations()

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
      appState.shuffle,
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
      newState.shuffle,
      newState.currFrameId,
      newState.currTrack,
      newState.currPlaylistTrackIdx,
      newState.playlist
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

  private def postNextOrPrevTrackStateUpdate(): Unit = {
    if (appState.playState == GsPlayState.PLAY)
      gsPlaybackRef ! NextOrPrevTrack(appState.currTrack, context.self)
    else if (appState.playState == GsPlayState.STOP || appState.playState == GsPlayState.PAUSE)
      gsPlaybackRef ! StopTrig(context.self)
      gsPlaybackRef ! SetFilePath(appState.currTrack.path, context.self)
      hydrateState()
  }

  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    msg match {
      case RestTrackSelect(track, replyTo) =>
        setAppState(
          stateMutation.setCurrTrack(appState, track)
        )
        gsPlaybackRef ! TrackSelect(track, context.self)
        hydrateState()
        replyTo ! RespondRestTrackSelect(track.path, context.self)
        Behaviors.same

      case RespondTrackSelect(path, _replyTo) =>
        setAppState(
          stateMutation.setPlayState(appState, GsPlayState.PLAY)
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
          stateMutation.addTrackToPlaylist(appState, track)
        )
        hydrateState()
        replyTo ! RespondAddTrackToPlaylist(context.self)
        Behaviors.same

      case SetPlaylist(playlist, replyTo) =>
        setAppState(
          stateMutation.setPlaylist(appState, playlist)
        )
        hydrateState()
        replyTo ! RespondSetPlaylist(context.self)
        Behaviors.same

      case ClearPlaylist(replyTo) =>
        setAppState(
          stateMutation.clearPlaylist(appState)
        )
        hydrateState()
        Behaviors.same

      case CurrPlaylistTrackIdx(newIdx, replyTo) =>
        setAppState(
          stateMutation.setCurrPlaylistTrackIdx(appState, newIdx)
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
          stateMutation.setPlayState(appState, GsPlayState.PLAY)
        )
        replyTo ! ReadPlaybackThreadState(context.self)
        hydrateState()
        Behaviors.same

      case RespondPlaybackThreadState(lastFrameId, playState, readComplete, replyTo) =>
        if (readComplete)
          gsDisplayRef ! SendReadComplete()
          // TODO:
          //    - [x] create playbackOption vars (loopAll | loopOne, shuffle)
          //    - [x] let user set those vars
          //    - [~] condition on those vars
          if (appState.playState == GsPlayState.PLAY)
            if (appState.playbackSpeed.value > 0)
              setAppState(
                stateMutation.nextTrack(appState)
              )
            else if (appState.playbackSpeed.value < 0)
              // TODO: start from end of track
              setAppState(
                stateMutation.prevTrack(appState)
              )

          postNextOrPrevTrackStateUpdate()
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
          stateMutation.setPlayState(appState, GsPlayState.PAUSE)
        )
        hydrateState()
        Behaviors.same

      case RespondStopTrig(replyTo) =>
        setAppState(
          stateMutation.setPlayState(appState, GsPlayState.STOP)
        )
        hydrateState()
        Behaviors.same

      case SetPlaybackSpeed(newPlaybackSpeed, _) =>
        setAppState(
          stateMutation.setPlaybackSpeed(appState, newPlaybackSpeed)
        )
        gsPlaybackRef ! SetPlaybackSpeed(newPlaybackSpeed, gsDisplayRef)
        hydrateState()
        Behaviors.same

      case SetLoopType(replyTo) =>
        setAppState(
          stateMutation.setLoopType(appState)
        )
        hydrateState()
        Behaviors.same

      case SetShuffle(replyTo) =>
        setAppState(
          stateMutation.setShuffle(appState)
        )
        hydrateState()
        Behaviors.same

      case PrevTrack() =>
        setAppState(
          stateMutation.prevTrack(appState)
        )
        postNextOrPrevTrackStateUpdate()
        Behaviors.same

      case NextTrack() =>
        setAppState(
          stateMutation.nextTrack(appState)
        )
        postNextOrPrevTrackStateUpdate()
        Behaviors.same

      case RespondTimerStart(timerId, replyTo) =>
        GsAppStateManagerTimer(timerId) match {
          case GsAppStateManagerTimer.currFrameIdCache =>
            val currFrameId = if (appState.playState == GsPlayState.STOP) 0L else GsPlaybackThread.getCurrFrameId.asInstanceOf[Long]
            setAppState(
              stateMutation.setCurrFrameId(appState, currFrameId)
            )
            if (appState.playState == GsPlayState.PLAY)
              currFrameIdCacheTimerStart()
            else if (appState.playState == GsPlayState.STOP)
            setAppState(
              stateMutation.setCurrFrameId(appState, 0)
            )

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
