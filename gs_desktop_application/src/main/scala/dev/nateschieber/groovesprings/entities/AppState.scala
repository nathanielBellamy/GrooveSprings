package dev.nateschieber.groovesprings.entities

case class AppState(currTrack: Track,
                    currPlaylistTrackIdx: Int,
                    playlist: Playlist)

import spray.json.{DefaultJsonProtocol, RootJsonFormat}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

trait AppStateJsonSupport extends SprayJsonSupport with DefaultJsonProtocol with TrackJsonSupport with PlaylistJsonSupport {
  implicit val appStateFormat: RootJsonFormat[AppState] = jsonFormat3(AppState.apply)
}

