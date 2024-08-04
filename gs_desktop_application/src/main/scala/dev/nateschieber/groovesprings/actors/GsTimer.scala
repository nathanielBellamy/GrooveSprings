package dev.nateschieber.groovesprings.actors

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import dev.nateschieber.groovesprings.traits.{TimerStart, GsCommand, RespondTimerStart}

object GsTimer {

  def apply(): Behavior[GsCommand] = Behaviors.setup {
    context =>
      given system: ActorSystem[Nothing] = context.system

      new GsTimer(context)
  }
}

class GsTimer(context: ActorContext[GsCommand]) extends AbstractBehavior[GsCommand](context) {

  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    msg match {

      case TimerStart(ms, replyTo) =>
        Thread.sleep(ms)
        replyTo ! RespondTimerStart(context.self)
        Behaviors.same

      case default =>
        Behaviors.same
    }
  }
}
