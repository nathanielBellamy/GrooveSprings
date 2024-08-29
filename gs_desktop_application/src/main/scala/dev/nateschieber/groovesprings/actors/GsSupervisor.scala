package dev.nateschieber.groovesprings.actors

import akka.actor.typed.{Behavior, PostStop, Signal}
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import dev.nateschieber.groovesprings.traits.InitDisplay

object GsSupervisor {
  def apply(): Behavior[Nothing] = Behaviors.setup {
    context =>
      val vst3HostRef = context.spawn(GsVst3Host(), "gs_vst3_hst")
      val playbackRef = context.spawn(GsPlayback(), "gs_playback")
      val displayRef = context.spawn(GsDisplay(playbackRef), "gs_display")
      val appStateManagerRef = context.spawn(GsAppStateManager(playbackRef, displayRef), "gs_state_manager")
      val restControllerRef = context.spawn(GsRestController(appStateManagerRef, playbackRef, displayRef), "gs_rest_controller")

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