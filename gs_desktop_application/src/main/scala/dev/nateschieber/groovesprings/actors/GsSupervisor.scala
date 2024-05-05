package dev.nateschieber.groovesprings.actors

import akka.actor.typed.Behavior
import akka.actor.typed.PostStop
import akka.actor.typed.Signal
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors

object GsSupervisor {
  def apply(): Behavior[Nothing] =
    Behaviors.setup[Nothing](context => new GsSupervisor(context))
}

class GsSupervisor(context: ActorContext[Nothing]) extends AbstractBehavior[Nothing](context) {
  println("gs_desktop_application started")

  override def onMessage(msg: Nothing): Behavior[Nothing] = {
    Behaviors.unhandled
  }

  override def onSignal: PartialFunction[Signal, Behavior[Nothing]] = {
    case PostStop =>
      println("gs_desktop_application stopped")
      this
  }
}
