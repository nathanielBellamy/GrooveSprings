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

object GsPlaybackThread {

  val GsPlaybackThreadServiceKey = ServiceKey[GsCommand]("gs_playback_thread")

  def apply(): Behavior[GsCommand] = Behaviors.setup {
    context =>
      new GsPlaybackThread(context)
  }
}

class GsPlaybackThread(context: ActorContext[GsCommand]) extends AbstractBehavior[GsCommand](context) {

  private var currFrameId: java.lang.Integer = 0

  def setCurrFrameId(newId: java.lang.Integer): Unit = {
    println("fooooooo baaaaaar")
    currFrameId = newId
  }
  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    msg match {
      case InitPlaybackThread(replyTo) =>
        JniMain.initPlaybackLoop()
        replyTo ! RespondInitPlaybackThread(context.self)
        Behaviors.same

      case StopPlaybackThread(replyTo) =>
        replyTo ! RespondStopPlaybackThread(context.self)
        context.stop(self)
        Behaviors.same
    }
  }

}