package dev.nateschieber.groovesprings.actors

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import dev.nateschieber.groovesprings.traits.{GsCommand, RespondFastForwardTrig, RespondPauseTrig, RespondPlayTrig, RespondRewindTrig, RespondStopTrig}

object GsTransportControl {

  val GsTransportControlServiceKey = ServiceKey[GsCommand]("gs_transport_control")

  def apply(): Behavior[GsCommand] = Behaviors.setup {
    context =>
      context.system.receptionist ! Receptionist.Register(GsTransportControlServiceKey, context.self)
      new GsTransportControl(context)
  }
}

class GsTransportControl(context: ActorContext[GsCommand]) extends AbstractBehavior[GsCommand](context) {

  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    msg match {
      case RespondPlayTrig(replyTo) =>
        println("GsTransportControl :: respondPlayTrig")
        Behaviors.same

      case RespondPauseTrig(replyTo) =>
        println("GsTransportControl :: respondPauseTrig")
        Behaviors.same

      case RespondStopTrig(replyTo) =>
        println("GsTransportControl :: respondStopTrig")
        Behaviors.same

      case RespondFastForwardTrig(replyTo) =>
        println("GsTransportControl :: respondFastForwardTrig")
        Behaviors.same

      case RespondRewindTrig(replyTo) =>
        println("GsTransportControl :: respondRewindTrig")
        Behaviors.same
    }
  }
}
