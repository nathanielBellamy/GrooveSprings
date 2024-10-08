package dev.nateschieber.groovesprings.actors

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.PostStop
import akka.actor.typed.Signal
import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import dev.nateschieber.groovesprings.enums.GsPlayState
import dev.nateschieber.groovesprings.traits.*

import java.util.UUID

object GsPlayback {

  val GsPlaybackServiceKey = ServiceKey[GsCommand]("gs_playback")

  def apply(): Behavior[GsCommand] = Behaviors.setup {
    context =>
      context.system.receptionist ! Receptionist.Register(GsPlaybackServiceKey, context.self)
      new GsPlayback(context)
  }
}

class GsPlayback(context: ActorContext[GsCommand]) extends AbstractBehavior[GsCommand](context) {

  private var playbackThreadRef: ActorRef[GsCommand] = null

  def clearPlaybackThread(): Unit = {
    if (playbackThreadRef != null)
      context.stop(playbackThreadRef)
      playbackThreadRef = null
  }

  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    msg match {
      case ReadPlaybackThreadState(replyTo) =>
        replyTo ! RespondPlaybackThreadState(GsPlaybackThread.getCurrFrameId,
                                             GsPlaybackThread.getPlayState,
                                             GsPlaybackThread.getReadComplete,
                                             context.self)
        Behaviors.same

      case TrackSelect(track, replyTo) =>
        GsPlaybackThread.stop()
        clearPlaybackThread()
        GsPlaybackThread.setCurrFrameId(0)
        GsPlaybackThread.setFilePath(track.path)
        replyTo ! RespondTrackSelect(track.path, context.self)
        Behaviors.same

      case NextOrPrevTrack(track, replyTo) =>
        GsPlaybackThread.stop()
        clearPlaybackThread()
        GsPlaybackThread.setCurrFrameId(0)
        GsPlaybackThread.setFilePath(track.path)
        replyTo ! RespondNextOrPrevTrack(track.path, context.self)
        Behaviors.same

      case InitialTrackSelect(track) =>
        GsPlaybackThread.stop()
        clearPlaybackThread()
        GsPlaybackThread.setFilePath(track.path)
        Behaviors.same

      case PlayTrig(replyTo) =>
        val playState = GsPlaybackThread.getPlayState
        if (playState == GsPlayState.PLAY)
          return Behaviors.same
        if (playState == GsPlayState.STOP)
          GsPlaybackThread.stop() // clear currFrameId, may have been updated by native thread
          clearPlaybackThread()
        GsPlaybackThread.play()
        if (GsPlaybackThread.getFilePath != null)
          if (playbackThreadRef == null)
            playbackThreadRef = context.spawn(GsPlaybackThread(), UUID.randomUUID().toString)
          playbackThreadRef ! InitPlaybackThread(context.self)
        replyTo ! RespondPlayTrig(context.self)
        Behaviors.same

      case PlayFromTrackSelectTrig(path, replyTo) =>
        GsPlaybackThread.stop() // clear currFrameId, may have been updated by native thread
        clearPlaybackThread()
        GsPlaybackThread.play()
        if (playbackThreadRef == null)
          playbackThreadRef = context.spawn(GsPlaybackThread(), UUID.randomUUID().toString)
        playbackThreadRef ! InitPlaybackThreadFromTrackSelect(path, context.self)
        replyTo ! RespondPlayFromTrackSelectTrig(context.self)
        Behaviors.same

      case PlayFromNextOrPrevTrack(path, replyTo) =>
        GsPlaybackThread.stop() // clear currFrameId, may have been updated by native thread
        clearPlaybackThread()
        GsPlaybackThread.play()
        if (playbackThreadRef == null)
          playbackThreadRef = context.spawn(GsPlaybackThread(), UUID.randomUUID().toString)
        playbackThreadRef ! InitPlaybackThreadFromTrackSelect(path, context.self)
        replyTo ! RespondPlayFromNextOrPrevTrack(context.self)
        Behaviors.same

      case PauseTrig(replyTo) =>
        GsPlaybackThread.pause()
        clearPlaybackThread()
        replyTo ! RespondPauseTrig(context.self)
        Behaviors.same

      case StopTrig(replyTo) =>
        GsPlaybackThread.stop()
        clearPlaybackThread()
        replyTo ! RespondStopTrig(context.self)
        Behaviors.same

      case SetPlaybackSpeed(speed, replyTo) =>
        GsPlaybackThread.setPlaybackSpeed(speed)
        Behaviors.same

      case SetFilePath(path, replyTo) =>
        GsPlaybackThread.setFilePath(path)
        Behaviors.same
    }
  }

  override def onSignal: PartialFunction[Signal, Behavior[GsCommand]] = {
    case PostStop =>
      this
  }
}
