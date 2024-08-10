package dev.nateschieber.groovesprings.enums

import spray.json.{DefaultJsonProtocol, JsString, JsValue, RootJsonFormat, deserializationError}

enum GsLoopType(_id: String):
  def id: String = _id

  case ONE  extends GsLoopType("ONE")
  case ALL  extends GsLoopType("ALL")
  case NONE extends GsLoopType("NONE")


object GsLoopTypeProtocol extends DefaultJsonProtocol {
  implicit object GsLoopTypeJsonFormat extends RootJsonFormat[GsLoopType] {
    def write(gslt: GsLoopType) = JsString(gslt.id)

    def read(jsValue: JsValue) = jsValue match {
      case JsString(value) => value match {
        case "ONE" => GsLoopType.ONE
        case "ALL" => GsLoopType.ALL
        case default => GsLoopType.NONE
      }
      case _ => deserializationError("GsLoopType expected")
    }
  }
}
