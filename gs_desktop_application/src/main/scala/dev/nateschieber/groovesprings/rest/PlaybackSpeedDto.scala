package dev.nateschieber.groovesprings.rest

case class PlaybackSpeedDto(speed: Double)
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait PlaybackSpeedJsonSupport extends SprayJsonSupport with DefaultJsonProtocol   {
  implicit val playbackSpeedFormat: RootJsonFormat[PlaybackSpeedDto] = jsonFormat1(PlaybackSpeedDto.apply)
}
