package dev.nateschieber.groovesprings.enums

import spray.json.{DefaultJsonProtocol, JsNumber, JsValue, RootJsonFormat, deserializationError}

enum GsLoopType(_id: Int):
  def id: Int = _id

  case ONE  extends GsLoopType(1)
  case ALL  extends GsLoopType(2)
  case NONE extends GsLoopType(0)


object GsLoopTypeProtocol extends DefaultJsonProtocol {
  implicit object GsLoopTypeJsonFormat extends RootJsonFormat[GsLoopType] {
    def write(gslt: GsLoopType) = JsNumber(gslt.id)

    def read(jsValue: JsValue) = jsValue match {
      case JsNumber(value) => value match {
        case 1 => GsLoopType.ONE
        case 2 => GsLoopType.ALL
        case default => GsLoopType.NONE
      }
      case _ => deserializationError("GsLoopType expected")
    }
  }
}
