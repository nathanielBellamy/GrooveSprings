package dev.nateschieber.groovesprings.actors

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import dev.nateschieber.groovesprings.enums.{GsPlayState, GsPlaybackSpeed}
import dev.nateschieber.groovesprings.jni.JniMain
import dev.nateschieber.groovesprings.traits.*

import scala.annotation.static

object GsPlaybackThread {
  // NOTE
  //  - these variables are static so as to be accessible from native code
  //  - the same is true for their static getter+setter methods
  //  - these methods should appear only in GsPlayback
  //  - GsPlayback cannot send messages either to deliver or to mutate these values
  //    because this thread will be blocked by native playback loop

  @static private var playState: GsPlayState = GsPlayState.STOP
  @static private var readComplete: Boolean = false
  @static private var playbackSpeed: GsPlaybackSpeed = GsPlaybackSpeed._1
  @static private var currFrameId: java.lang.Long = 0
  @static private var filePath: java.lang.String = null
  @static private var audioCodec: java.lang.String = null

  // NOTE:
  //   - scala code only cares about the "current" native thread with threadId equal to the result of getThreadId()
  //   - Actor cleanup is asynchronous and thus so is native thread cleanup for us here
  //   - while running, each native thread is checking its Audio::threadId against the result of getThreadId()
  //   - once there is a mismatch, the thread
  //      1. no longer updates values here
  //      2. cleans up its resources
  //      3. terminates
  //   - this structure prevents the occurrence of detached native threads continuing
  //     to write to audio out, which may result from race conditions when multiple native threads
  //     read and write to playState during async cleanup on rapid user track selections
  //     in short, native threads would never receive the signal to break their while loop and cleanup
  @static private var threadId: Long = synchronized { 0 }
  @static private def getThreadId: Long = synchronized {
    threadId
  }
  @static private def incrThreadId(): Unit = synchronized {
    threadId += 1
  }
  @static private def newThreadId: Long = synchronized {
    incrThreadId()
    threadId
  }

  @static def stop(): Unit = {
//    incrThreadId()
    currFrameId = 0
    playState = GsPlayState.STOP
  }

  @static def pause(): Unit = {
//    incrThreadId()
    playState = GsPlayState.PAUSE
  }

  @static def play(): Unit = {
    readComplete = false
    playState = GsPlayState.PLAY
  }

  @static def getPlayState: GsPlayState = {
    playState
  }

  @static def getPlayStateInt: Int = {
    playState.id
  }

  @static def setPlayState(newState: GsPlayState): Unit = {
    playState = newState
  }

  @static def setPlayStateInt(newState: Int): Unit = {
    playState = playStateFromInt(newState)
  }

  @static def getReadComplete: Boolean = {
    readComplete
  }

  @static def setReadComplete(newRC: Boolean): Unit = {
    readComplete = newRC
  }

  @static def setPlaybackSpeed(speed: GsPlaybackSpeed): Unit = {
    playbackSpeed = speed
  }

  @static def getPlaybackSpeed: GsPlaybackSpeed = {
    playbackSpeed
  }

  @static def getPlaybackSpeedFloat: Float = {
    playbackSpeed.value
  }

  @static def getCurrFrameId: java.lang.Long = {
    // NOTE:
    // - conditioning on playState handles the transition from PLAY -> STOP
    // - native thread may still update currFrameId after Scala sets to 0
    //   before the ActorSystem stops + cleans up the current instance of this Actor
    playState match {
      case GsPlayState.STOP => 0
      case default => currFrameId
    }
  }
  
  @static def setCurrFrameId(newId: java.lang.Long): Unit = {
    if (newId == null) {
      currFrameId = 0
    } else {
      currFrameId = newId
    }
  }
  
  @static def getFilePath: java.lang.String = {
    filePath
  }
  
  @static def setFileName(value: java.lang.String): Unit = {
    filePath = value
  }
  
  @static def getAudioCodec: java.lang.String = {
    audioCodec
  }
  
  @static def setAudioCodec(value: java.lang.String): Unit = {
    audioCodec = value
  }

  @static private def playStateFromInt(i: Int): GsPlayState = {
    i match {
      case 4       => GsPlayState.FF
      case 3       => GsPlayState.RW
      case 2       => GsPlayState.PAUSE
      case 1       => GsPlayState.PLAY
      case default => GsPlayState.STOP
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
      case InitPlaybackThread(replyTo) =>
        val newThreadId = GsPlaybackThread.newThreadId
        println(newThreadId)
        JniMain.initPlaybackLoop(
          newThreadId,
//          GsPlaybackThread.newThreadId(),
          GsPlaybackThread.getFilePath,
          GsPlaybackThread.getCurrFrameId
        ) // blocking
        Behaviors.stopped
    }
  }
}