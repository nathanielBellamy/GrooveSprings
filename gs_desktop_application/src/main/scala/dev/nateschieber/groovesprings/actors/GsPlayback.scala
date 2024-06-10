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

  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    msg match {
      case ReadFrameId(replyTo) =>
        replyTo ! RespondFrameId(GsPlaybackThread.getCurrFrameId(), GsPlaybackThread.getPlayState(), context.self)
        Behaviors.same

      case FileSelect(fileName, audioCodec, replyTo) =>
        GsPlaybackThread.stop()
        GsPlaybackThread.setFileName(fileName)
        GsPlaybackThread.setAudioCodec(audioCodec)
        if (playbackThreadRef != null)
          context.stop(playbackThreadRef)
          playbackThreadRef = null
        replyTo ! RespondFileSelect(context.self)
        Behaviors.same

      case PlayTrig(replyTo) =>
        println("GsPlayback :: play")
        if (playbackThreadRef == null)
          playbackThreadRef = context.spawn(GsPlaybackThread(), UUID.randomUUID().toString())
        playbackThreadRef ! InitPlaybackThread(context.self)
        GsPlaybackThread.setPlayState(GsPlayState.PLAY)
        replyTo ! RespondPlayTrig(context.self)
        Behaviors.same
      
      case PauseTrig(replyTo) =>
        println("GsPlayback :: pause")
        GsPlaybackThread.setPlayState(GsPlayState.PAUSE)
        if (playbackThreadRef != null)
          context.stop(playbackThreadRef)
          playbackThreadRef = null
        replyTo ! RespondPauseTrig(context.self)
        Behaviors.same

      case StopTrig(replyTo) =>
        println("GsPlayback :: stop")
        GsPlaybackThread.stop()
        if (playbackThreadRef != null)
          context.stop(playbackThreadRef)
          playbackThreadRef = null
        replyTo ! RespondStopTrig(context.self)
        Behaviors.same
        
      case FastForwardTrig(replyTo) =>
        println("GsPlayback :: fastForward")
        GsPlaybackThread.setPlayState(GsPlayState.FF)
        replyTo ! RespondFastForwardTrig(context.self)
        Behaviors.same

      case RewindTrig(replyTo) =>
        println("GsPlayback :: rewind")
        GsPlaybackThread.setPlayState(GsPlayState.RW)
        replyTo ! RespondRewindTrig(context.self)
        Behaviors.same
    }
  }

  override def onSignal: PartialFunction[Signal, Behavior[GsCommand]] = {
    case PostStop =>
      this
  }
}
