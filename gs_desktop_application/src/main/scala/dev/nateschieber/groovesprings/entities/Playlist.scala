package dev.nateschieber.groovesprings.entities

case class Playlist(
                   id: Long,
                   name: String,
                   tracks: Array[Track]
                   )
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait PlaylistJsonSupport extends SprayJsonSupport with DefaultJsonProtocol   {
  implicit object playlistFormat extends RootJsonFormat[Playlist] {
    // TODO
  }
}
