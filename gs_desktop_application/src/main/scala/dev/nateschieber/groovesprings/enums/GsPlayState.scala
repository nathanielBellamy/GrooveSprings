package dev.nateschieber.groovesprings.enums

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import dev.nateschieber.groovesprings.enums.GsPlayStateProtocol.GsPlayStateJsonFormat
import spray.json.{DefaultJsonProtocol, JsNumber, JsValue, RootJsonFormat, deserializationError}

enum GsPlayState(idIn: Int):
  def id = idIn

  // Int values to be read in cpp
  case STOP extends GsPlayState(0)
  case PLAY extends GsPlayState(1)
  case PAUSE extends GsPlayState(2)
  case RW extends GsPlayState(3)
  case FF extends  GsPlayState(4)

object GsPlayStateProtocol extends DefaultJsonProtocol {
  implicit object GsPlayStateJsonFormat extends RootJsonFormat[GsPlayState] {
    def write(gsps: GsPlayState) = JsNumber(gsps.id)

    def read(jsValue: JsValue) = jsValue match {
      case JsNumber(value) => value match {
        case 1 => GsPlayState.PLAY
        case 2 => GsPlayState.PAUSE
        case 3 => GsPlayState.RW
        case 4 => GsPlayState.FF
        case default => GsPlayState.STOP
      }
      case _ => deserializationError("GsPlayState expected")
    }
  }
}
