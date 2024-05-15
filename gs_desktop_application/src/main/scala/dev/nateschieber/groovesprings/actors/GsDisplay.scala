package dev.nateschieber.groovesprings.actors

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.receptionist.Receptionist
import akka.actor.typed.receptionist.ServiceKey
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import dev.nateschieber.groovesprings.traits.*
import akka.util.Timeout

import java.util.concurrent.TimeUnit
import scala.language.postfixOps



object GsDisplay {

    val GsDisplayServiceKey = ServiceKey[GsCommand]("gs_display")

    private var GsPlaybackRef: ActorRef[GsCommand] = null

    def apply(): Behavior[GsCommand] = Behaviors.setup {
      context =>
        context.system.receptionist ! Receptionist.Register(GsDisplayServiceKey, context.self)
        new GsDisplay(context)
    }
}

class GsDisplay(context: ActorContext[GsCommand]) extends AbstractBehavior[GsCommand](context) {

  implicit val timeout: Timeout = Timeout.apply(100, TimeUnit.MILLISECONDS)

  private var stopped: Boolean = true

  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    msg match {
      case RespondPlayTrig(replyTo) =>
        replyTo ! ReadFrameId(context.self)
        stopped = false
        Behaviors.same

      case RespondStopTrig(replyTo) =>
        println("GsDisplay :: RespondStopTrig Received")
        stopped = true
        Behaviors.same

      case RespondFrameId(lastFrameId, replyTo) =>
        println("GsDisplay :: lastFrameId: " + lastFrameId)
        if (!stopped)
          Thread.sleep(100)
          replyTo ! ReadFrameId(context.self)
        Behaviors.same

      case InitDisplay(playbackRef) =>
        println("GsDisplay :: InitDisplay received")
        playbackRef ! ReadFrameId(context.self)
        Behaviors.same
    }
  }
}


