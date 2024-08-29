package dev.nateschieber.groovesprings.actors

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import dev.nateschieber.groovesprings.traits.{GsCommand, InitVst3Host}

object GsVst3Host {

  def apply(): Behavior[GsCommand] = Behaviors.setup {
    context =>
      given system: ActorSystem[Nothing] = context.system

      val gsVst3Host = new GsVst3Host(context)
      val gsVst3HostThread = context.spawn(GsVst3HostThread(), "gsVst3HostThread")
      gsVst3Host.setGsVst3HostThreadRef(gsVst3HostThread)
      gsVst3Host.initGsVst3HostThread()
      gsVst3Host
  }
}

class GsVst3Host (context: ActorContext[GsCommand]) extends AbstractBehavior[GsCommand](context) {

  private var gsVst3HostThreadRef: ActorRef[GsCommand] = null

  def setGsVst3HostThreadRef(ref: ActorRef[GsCommand]): Unit = {
    gsVst3HostThreadRef = ref
  }

  def getGsVst3HostThreadRef(): ActorRef[GsCommand] = {
    gsVst3HostThreadRef
  }

  def initGsVst3HostThread(): Unit = {
    gsVst3HostThreadRef ! InitVst3Host(context.self)
  }

  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    msg match {

      case default =>
        println("GsVst3Host Unmatched Message \n" + msg + "\n End GsVst3Host Unmatched Message \n")
        Behaviors.same
    }
  }

}
