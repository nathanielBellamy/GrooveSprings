package dev.nateschieber.groovesprings.entities

case class Playlist(id: Long,
                    name: String,
                    tracks: List[Track])

val EmptyPlaylist = Playlist(0, "", List[Track]())

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, JsonFormat, RootJsonFormat}

trait PlaylistJsonSupport extends SprayJsonSupport with DefaultJsonProtocol with TrackJsonSupport   {
  implicit val playlistFormat: RootJsonFormat[Playlist] = jsonFormat3(Playlist.apply)
}
