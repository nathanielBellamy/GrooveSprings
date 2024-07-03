package dev.nateschieber.groovesprings.entities

case class Track(id: Long,
                 path: String,
                 json: String)

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait TrackJsonSupport extends SprayJsonSupport with DefaultJsonProtocol   {
  implicit val trackFormat: RootJsonFormat[Track] = jsonFormat3(Track.apply)
}
