package dev.nateschieber.groovesprings.actors

import akka.actor.TypedActor.self
import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.PostStop
import akka.actor.typed.Signal
import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import dev.nateschieber.groovesprings.jni.JniMain
import dev.nateschieber.groovesprings.traits.*

import scala.annotation.static

object GsPlaybackThread {

  @static var currFrameId: java.lang.Integer = 0

  @static def setCurrFrameId(newId: java.lang.Integer): Unit = {
    if (newId == null) {
      println("\n GsPlaybackThread null id")
    } else {
      currFrameId = newId
      println("\n GsPlaybackThread newId: " + currFrameId)
    }
  }

  val GsPlaybackThreadServiceKey = ServiceKey[GsCommand]("gs_playback_thread")

  def apply(): Behavior[GsCommand] = Behaviors.setup {
    context =>
      new GsPlaybackThread(context)
  }
}

class GsPlaybackThread(context: ActorContext[GsCommand]) extends AbstractBehavior[GsCommand](context) {

  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    msg match {
      case InitPlaybackThread(replyTo) =>
        JniMain.initPlaybackLoop()
        replyTo ! RespondInitPlaybackThread(context.self)
        Behaviors.same

      case StopPlaybackThread(replyTo) =>
        GsPlaybackThread.setCurrFrameId(0)
        replyTo ! RespondStopPlaybackThread(context.self)
        context.stop(self)
        Behaviors.same
    }
  }

}