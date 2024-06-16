package dev.nateschieber.groovesprings.rest

case class FileSelectDto(path: String)
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait FileSelectJsonSupport extends SprayJsonSupport with DefaultJsonProtocol   {
  implicit val fileSelectFormat: RootJsonFormat[FileSelectDto] = jsonFormat1(FileSelectDto.apply)
}
