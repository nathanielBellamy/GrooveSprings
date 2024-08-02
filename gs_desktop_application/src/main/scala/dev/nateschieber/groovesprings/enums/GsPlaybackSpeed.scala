package dev.nateschieber.groovesprings.enums

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import dev.nateschieber.groovesprings.actors.GsPlaybackThread.{currFrameId, playState}
import dev.nateschieber.groovesprings.enums.GsPlaybackSpeedProtocol.GsPlaybackSpeedJsonFormat
import spray.json.{DefaultJsonProtocol, JsArray, JsNumber, JsString, JsValue, RootJsonFormat, deserializationError}

import scala.annotation.static

enum GsPlaybackSpeed(valueIn: Float):
  def value = valueIn

  case _2   extends GsPlaybackSpeed(2.0)  // double speed
  case _1   extends GsPlaybackSpeed(1.0)  // normal playback
  case _05  extends GsPlaybackSpeed(0.5)  // half-speed
  case _N05 extends GsPlaybackSpeed(-0.5) // half-speed reverse
  case _N1  extends GsPlaybackSpeed(-1.0) // reverse
  case _N2  extends GsPlaybackSpeed(-2.0) // double speed reverse

object GsPlaybackSpeedProtocol extends DefaultJsonProtocol {
  implicit object GsPlaybackSpeedJsonFormat extends RootJsonFormat[GsPlaybackSpeed] {
    def write(gsps: GsPlaybackSpeed) = JsNumber(gsps.value)

    def read(jsValue: JsValue) = jsValue match {
      case JsNumber(value) => value match {
        case -2.0 => GsPlaybackSpeed._N2
        case -1.0 => GsPlaybackSpeed._N1
        case -0.5 => GsPlaybackSpeed._N05
        case  2.0 => GsPlaybackSpeed._N2
        case default => GsPlaybackSpeed._1
      }
      case _ => deserializationError("GsPlaybackSpeed expected")
    }
  }
}
//  case _1  extends GsPlaybackSpeed(1)
//  case _2  extends GsPlaybackSpeed(2)
//  case _4  extends GsPlaybackSpeed(4)
//  case _8  extends GsPlaybackSpeed(8)
//  case _N1 extends GsPlaybackSpeed(-1)
//  case _N2 extends GsPlaybackSpeed(-2)
//  case _N4 extends GsPlaybackSpeed(-4)
//  case _N8 extends GsPlaybackSpeed(-8)
