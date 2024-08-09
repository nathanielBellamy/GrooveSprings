package dev.nateschieber.groovesprings.actors

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import dev.nateschieber.groovesprings.traits.{TimerStart, GsCommand, RespondTimerStart}

object GsTimer {

  def apply(id: String): Behavior[GsCommand] = Behaviors.setup {
    context =>
      given system: ActorSystem[Nothing] = context.system

      new GsTimer(id, context)
  }
}

class GsTimer(id: String, context: ActorContext[GsCommand]) extends AbstractBehavior[GsCommand](context) {

  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    msg match {

      case TimerStart(ms, replyTo) =>
        Thread.sleep(ms)
        replyTo ! RespondTimerStart(id, context.self)
        Behaviors.same

      case default =>
        Behaviors.same
    }
  }
}
