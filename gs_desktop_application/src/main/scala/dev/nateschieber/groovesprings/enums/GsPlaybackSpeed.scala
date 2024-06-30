package dev.nateschieber.groovesprings.enums

import dev.nateschieber.groovesprings.actors.GsPlaybackThread.{currFrameId, playState}

import scala.annotation.static

enum GsPlaybackSpeed(valueIn: Float):
  def value = valueIn

  case _2   extends GsPlaybackSpeed(2.0)  // double speed
  case _1   extends GsPlaybackSpeed(1.0)  // normal playback
  case _05  extends GsPlaybackSpeed(0.5)  // half-speed
  case _N05 extends GsPlaybackSpeed(-0.5) // half-speed reverse
  case _N1  extends GsPlaybackSpeed(-1.0) // reverse
  case _N2  extends GsPlaybackSpeed(-2.0) // double speed reverse

//  case _1  extends GsPlaybackSpeed(1)
//  case _2  extends GsPlaybackSpeed(2)
//  case _4  extends GsPlaybackSpeed(4)
//  case _8  extends GsPlaybackSpeed(8)
//  case _N1 extends GsPlaybackSpeed(-1)
//  case _N2 extends GsPlaybackSpeed(-2)
//  case _N4 extends GsPlaybackSpeed(-4)
//  case _N8 extends GsPlaybackSpeed(-8)
