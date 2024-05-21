package dev.nateschieber.groovesprings.actors

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import dev.nateschieber.groovesprings.jni.JniMain
import dev.nateschieber.groovesprings.traits.*

import scala.annotation.static

object GsPlaybackThread {

  @static var stopped: Boolean = true

  @static def getStopped(): Boolean = {
    stopped
  }
  
  @static def setStopped(value: Boolean) = {
    stopped = value
  }

  @static var currFrameId: java.lang.Long = 0

  @static def setCurrFrameId(newId: java.lang.Long): Unit = {
    if (newId == null) {
      currFrameId = 0
    } else {
      currFrameId = newId
    }
  }

  def apply(): Behavior[GsCommand] = Behaviors.setup {
    context =>
      new GsPlaybackThread(context)
  }
}

class GsPlaybackThread(context: ActorContext[GsCommand]) extends AbstractBehavior[GsCommand](context) {

  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    msg match {
      case InitPlaybackThread(fileName, audioCodec, replyTo) =>
        // TODO: pass fileName and audioCodec to cpp
        println(s"\n Playback Thread: filename: ${fileName} + audioCodec: ${audioCodec} \n")
        JniMain.initPlaybackLoop(s"${fileName}.${audioCodec}") // blocking
        Behaviors.stopped
    }
  }
}