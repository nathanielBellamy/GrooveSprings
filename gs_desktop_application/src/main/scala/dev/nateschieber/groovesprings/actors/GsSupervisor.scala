package dev.nateschieber.groovesprings.actors

import akka.actor.typed.Behavior
import akka.actor.typed.PostStop
import akka.actor.typed.Signal
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import dev.nateschieber.groovesprings.traits.{InitDisplay, PlayTrig, StopTrig}

object GsSupervisor {
  def apply(): Behavior[Nothing] = Behaviors.setup {
    context =>
      val playbackRef = context.spawn(GsPlayback(), "gs_playback")
      val displayRef = context.spawn(GsDisplay(playbackRef), "gs_display")
      val transportControlRef = context.spawn(GsTransportControl(playbackRef, displayRef), "gs_transport_control")

      displayRef ! InitDisplay()

      new GsSupervisor(context)
  }
}

class GsSupervisor(context: ActorContext[Nothing]) extends AbstractBehavior[Nothing](context) {

  override def onMessage(msg: Nothing): Behavior[Nothing] = {
    Behaviors.unhandled
  }

  override def onSignal: PartialFunction[Signal, Behavior[Nothing]] = {
    case PostStop =>
      println("gs_desktop_application stopped")
      this
  }
}