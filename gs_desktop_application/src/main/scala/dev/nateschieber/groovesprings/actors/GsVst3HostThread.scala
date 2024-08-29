package dev.nateschieber.groovesprings.actors

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{ActorSystem, Behavior}
import dev.nateschieber.groovesprings.jni.JniMain
import dev.nateschieber.groovesprings.traits.{GsCommand, InitVst3Host}

object GsVst3HostThread {

  def apply(): Behavior[GsCommand] = Behaviors.setup {
    context =>
      given system: ActorSystem[Nothing] = context.system

      new GsVst3HostThread(context)
  }
}

class GsVst3HostThread (context: ActorContext[GsCommand]) extends AbstractBehavior[GsCommand](context) {

  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    msg match {

      case InitVst3Host(replyTo) =>
        JniMain.initVst3Host()
        Behaviors.same

      case default => 
        println("GsVst3HostThread Unmatched Message \n" + msg + "\n End GsVst3Host Unmatched Message \n")
        Behaviors.same
    }
  }

}
