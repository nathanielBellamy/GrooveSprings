package dev.nateschieber.groovesprings.enums

enum GsPlaybackSpeed(valueIn: Int):
  def value = valueIn
  
  case _1  extends GsPlaybackSpeed(1)
  case _2  extends GsPlaybackSpeed(2)
  case _4  extends GsPlaybackSpeed(4)
  case _8  extends GsPlaybackSpeed(8)
  case _N1 extends GsPlaybackSpeed(-1)
  case _N2 extends GsPlaybackSpeed(-2)
  case _N4 extends GsPlaybackSpeed(-4)
  case _N8 extends GsPlaybackSpeed(-8)
