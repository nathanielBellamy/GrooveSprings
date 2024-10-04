package dev.nateschieber.groovesprings.actors

import akka.Done
import akka.actor.CoordinatedShutdown
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{ActorSystem, Behavior}
import dev.nateschieber.groovesprings.actors.GsVst3HostThread.vst3HostAppPtr
import dev.nateschieber.groovesprings.jni
import dev.nateschieber.groovesprings.jni.JniMain
import dev.nateschieber.groovesprings.traits.{GsCommand, InitVst3Host}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.annotation.static
import scala.concurrent.Future

object GsVst3HostThread {

  @static private var vst3HostAppPtr: Long = synchronized { 0l }

  @static def setVst3HostPtr(ptr: Long): Unit = {
    vst3HostAppPtr = ptr
  }

  @static def getVst3HostPtr: Long = {
    vst3HostAppPtr
  }

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
//        vst3HostAppPtr = JniMain.allocVst3Host()
//        JniMain.initVst3Host(vst3HostAppPtr)
        Behaviors.same

      case default => 
        println("GsVst3HostThread Unmatched Message \n" + msg + "\n End GsVst3Host Unmatched Message \n")
        Behaviors.same
    }
  }


  CoordinatedShutdown(context.system).addTask(CoordinatedShutdown.PhaseBeforeServiceUnbind, "cacheStateOnShutdown") { () =>
    Future {
      JniMain.deleteVst3Host(vst3HostAppPtr)
      Done
    }
  }

}