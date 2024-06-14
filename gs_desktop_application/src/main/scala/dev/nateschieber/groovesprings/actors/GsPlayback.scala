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
      case ReadFrameId(replyTo) =>
        replyTo ! RespondFrameId(GsPlaybackThread.getCurrFrameId(), GsPlaybackThread.getPlayState(), context.self)
        Behaviors.same

      case FileSelect(path, replyTo) =>
        GsPlaybackThread.stop()
        GsPlaybackThread.setFileName(path)
        clearPlaybackThread()
        replyTo ! RespondFileSelect(context.self)
        Behaviors.same

      case PlayTrig(replyTo) =>
        println("GsPlayback :: play")
        if (GsPlaybackThread.getPlayState() == GsPlayState.STOP)
          GsPlaybackThread.stop() // clear currFrameId, may have been updated by native thread
        GsPlaybackThread.play()
        if (playbackThreadRef == null)
          playbackThreadRef = context.spawn(GsPlaybackThread(), UUID.randomUUID().toString())
        playbackThreadRef ! InitPlaybackThread(context.self)
        replyTo ! RespondPlayTrig(context.self)
        Behaviors.same
      
      case PauseTrig(replyTo) =>
        println("GsPlayback :: pause")
        GsPlaybackThread.pause()
        clearPlaybackThread()
        replyTo ! RespondPauseTrig(context.self)
        Behaviors.same

      case StopTrig(replyTo) =>
        println("GsPlayback :: stop")
        GsPlaybackThread.stop()
        clearPlaybackThread()
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

      case SetPlaybackSpeed(speed, replyTo) =>
        println(s"GsPlayback :: SetPlaybackSpeed :: speed :: ${speed}" )
        GsPlaybackThread.setPlaybackSpeed(speed)
        Behaviors.same
    }
  }

  override def onSignal: PartialFunction[Signal, Behavior[GsCommand]] = {
    case PostStop =>
      this
  }
}
