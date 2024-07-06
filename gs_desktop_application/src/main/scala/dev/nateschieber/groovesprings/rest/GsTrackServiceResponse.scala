package dev.nateschieber.groovesprings.rest

import spray.json.{DefaultJsonProtocol, JsonFormat, RootJsonFormat}

case class GsTrackServiceResponse[T](
                                 apiVersion: String,
                                 responseAt: String,
                                 data: T
                                 )

object GsTrackServiceResponseProtocol extends DefaultJsonProtocol {
  implicit def gsTrackServiceResponseFormat[T :JsonFormat]: RootJsonFormat[GsTrackServiceResponse[T]] = jsonFormat3(GsTrackServiceResponse.apply[T])
}
