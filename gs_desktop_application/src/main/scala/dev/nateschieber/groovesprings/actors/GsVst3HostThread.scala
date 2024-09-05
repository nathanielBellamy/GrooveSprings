package dev.nateschieber.groovesprings.actors

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{ActorSystem, Behavior}
import dev.nateschieber.groovesprings.actors.GsVst3HostThread.vst3HostAppPtr
import dev.nateschieber.groovesprings.jni
import dev.nateschieber.groovesprings.jni.{JniMain, Vst3AudioHostAppPtr}
import dev.nateschieber.groovesprings.traits.{GsCommand, InitVst3Host}

import scala.annotation.static

object GsVst3HostThread {

  @static private var vst3HostAppPtr: Vst3AudioHostAppPtr = synchronized { null }

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
        vst3HostAppPtr = JniMain.allocVst3Host()
        println("vst3HostAddr" + vst3HostAppPtr.getAddress)
        Behaviors.same

      case default => 
        println("GsVst3HostThread Unmatched Message \n" + msg + "\n End GsVst3Host Unmatched Message \n")
        Behaviors.same
    }
  }

}
