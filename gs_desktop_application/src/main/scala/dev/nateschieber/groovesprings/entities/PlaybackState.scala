package dev.nateschieber.groovesprings.entities

import dev.nateschieber.groovesprings.entities.{Playlist, Track}

case class PlaybackState(
                           currTrack: Track,
                           currPlaylistTrackIdx: Int,
                           playlist: Playlist
                           )

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait PlaybackStateJsonSupport extends SprayJsonSupport with DefaultJsonProtocol   {
  implicit val playbackStateFormat: RootJsonFormat[PlaybackState] = jsonFormat3(PlaybackState.apply)
}
