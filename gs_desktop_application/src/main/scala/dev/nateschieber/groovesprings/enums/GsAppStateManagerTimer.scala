package dev.nateschieber.groovesprings.enums

import scala.annotation.static

object GsAppStateManagerTimer {
  @static def fromId(timerId: String): GsAppStateManagerTimer = {
    timerId match {
      case "currFrameIdCache"   => GsAppStateManagerTimer.currFrameIdCache
      case "playbackThreadPoll" => GsAppStateManagerTimer.playbackThreadPoll

      case default              => GsAppStateManagerTimer.nullTimer
    }
  }
}

enum GsAppStateManagerTimer(idIn: String):
  def id: String = idIn

  case currFrameIdCache   extends GsAppStateManagerTimer("currFrameIdCache")
  case playbackThreadPoll extends GsAppStateManagerTimer("playbackThreadPoll")
  case nullTimer          extends GsAppStateManagerTimer("null")