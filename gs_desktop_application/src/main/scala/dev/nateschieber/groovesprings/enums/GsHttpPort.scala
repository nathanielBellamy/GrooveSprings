package dev.nateschieber.groovesprings.enums

enum GsHttpPort(number: Int):
  def port = number

  case GsDesktopApplication extends GsHttpPort(5678)
  case GsRestController extends GsHttpPort(8765)
  case GsTransportControl extends GsHttpPort(8766)