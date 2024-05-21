package dev.nateschieber.groovesprings.actors

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import dev.nateschieber.groovesprings.jni.JniMain
import dev.nateschieber.groovesprings.traits.*

import scala.annotation.static

object GsPlaybackThread {
  // NOTE
  //  - these variables are static so as to be accessable from native code
  //  - the same is true for their static getter+setter methods
  //  - these methods should appear only in GsPlayback
  //  - GsPlayback cannot send messages to mutate these values
  //    because this thread will be blocked by native playback loop
  @static private var stopped: Boolean = true
  @static private var currFrameId: java.lang.Long = 0
  @static private var fileName: java.lang.String = null
  @static private var audioCodec: java.lang.String = null
  
  @static def stop(): Unit = {
    stopped = true
    currFrameId = 0
  }

  @static def getStopped(): Boolean = {
    stopped
  }
  
  @static def setStopped(value: Boolean) = {
    stopped = value
  }

  @static def getCurrFrameId(): java.lang.Long = {
    currFrameId
  }
  
  @static def setCurrFrameId(newId: java.lang.Long): Unit = {
    if (newId == null) {
      currFrameId = 0
    } else {
      currFrameId = newId
    }
  }
  
  @static def getFileName(): java.lang.String = {
    fileName
  }
  
  @static def setFileName(value: java.lang.String) = {
    fileName = value
  }
  
  @static def getAudioCodec(): java.lang.String = {
    audioCodec
  }
  
  @static def setAudioCodec(value: java.lang.String) = {
    audioCodec = value
  }

  def apply(): Behavior[GsCommand] = Behaviors.setup {
    context =>
      new GsPlaybackThread(context)
  }
}

class GsPlaybackThread(context: ActorContext[GsCommand]) extends AbstractBehavior[GsCommand](context) {

  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    msg match {
      case InitPlaybackThread(replyTo) =>
        JniMain.initPlaybackLoop(s"${GsPlaybackThread.getFileName()}.${GsPlaybackThread.getAudioCodec()}") // blocking
        Behaviors.stopped
    }
  }
}