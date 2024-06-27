package dev.nateschieber.groovesprings.rest

case class CacheStateDto(stateJson: String)
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait CacheStateJsonSupport extends SprayJsonSupport with DefaultJsonProtocol   {
  implicit val cacheStateFormat: RootJsonFormat[CacheStateDto] = jsonFormat1(CacheStateDto.apply)
}
