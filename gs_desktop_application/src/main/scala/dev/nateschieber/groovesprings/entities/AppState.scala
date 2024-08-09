package dev.nateschieber.groovesprings.entities
import dev.nateschieber.groovesprings.enums.{GsPlayState, GsPlaybackSpeed}

case class AppState(playState: GsPlayState,
                    playbackSpeed: GsPlaybackSpeed,
                    currFrameId: Long,
                    currTrack: Track,
                    currPlaylistTrackIdx: Int,
                    playlist: Playlist)

val EmptyAppState = AppState(GsPlayState.STOP, GsPlaybackSpeed._1, 0, EmptyTrack, 0, EmptyPlaylist)

import spray.json.{DefaultJsonProtocol, RootJsonFormat}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import dev.nateschieber.groovesprings.enums.GsPlayStateProtocol.GsPlayStateJsonFormat
import dev.nateschieber.groovesprings.enums.GsPlaybackSpeedProtocol.GsPlaybackSpeedJsonFormat
import dev.nateschieber.groovesprings.enums.GsLoopTypeProtocol.GsLoopTypeJsonFormat

trait AppStateJsonSupport
  extends SprayJsonSupport
    with DefaultJsonProtocol
    with TrackJsonSupport
    with PlaylistJsonSupport {
  implicit val appStateFormat: RootJsonFormat[AppState] = jsonFormat6(AppState.apply)
}

object AppStateJsonProtocol extends DefaultJsonProtocol with TrackJsonSupport with PlaylistJsonSupport {
  implicit val appStateFormat: RootJsonFormat[AppState] = jsonFormat6(AppState.apply)
}

