package dev.nateschieber.groovesprings.enums

enum GsPlayState(idIn: Int):
  def id = idIn

  // Int values to be read in cpp
  case STOP extends GsPlayState(0)
  case PLAY extends GsPlayState(1)
  case PAUSE extends GsPlayState(2)
  case RW extends GsPlayState(3)
  case FF extends  GsPlayState(4)