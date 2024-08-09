package dev.nateschieber.groovesprings.traits

import akka.actor.typed.ActorRef
import dev.nateschieber.groovesprings.entities.{Playlist, Track}
import dev.nateschieber.groovesprings.enums.{GsPlayState, GsPlaybackSpeed}

sealed trait GsCommand

// GsDisplay
final case class InitDisplay() extends GsCommand

// GsPlayback
final case class ReadPlaybackThreadState(replyTo: ActorRef[RespondPlaybackThreadState]) extends GsCommand
final case class RespondPlaybackThreadState(frameId: Long, playState: GsPlayState, readComplete: Boolean, replyTo: ActorRef[ReadPlaybackThreadState]) extends GsCommand

final case class TrackSelect(track: Track, replyTo: ActorRef[RespondTrackSelect]) extends GsCommand // auto-play
final case class RespondTrackSelect(path: String, replyTo: ActorRef[TrackSelect]) extends GsCommand
final case class NextOrPrevTrack(track: Track, replyTo: ActorRef[RespondNextOrPrevTrack]) extends GsCommand // auto-play
final case class RespondNextOrPrevTrack(path: String, replyTo: ActorRef[NextOrPrevTrack]) extends GsCommand // auto-play

final case class InitialTrackSelect(track: Track) extends GsCommand // no auto-play

// GsPlaybackThread
final case class InitPlaybackThread(replyTo: ActorRef[RespondInitPlaybackThread]) extends GsCommand
final case class RespondInitPlaybackThread(replyTo: ActorRef[InitPlaybackThread]) extends GsCommand
final case class InitPlaybackThreadFromTrackSelect(path: String, replyTo: ActorRef[RespondInitPlaybackThreadFromTrackSelect]) extends GsCommand
final case class RespondInitPlaybackThreadFromTrackSelect(replyTo: ActorRef[InitPlaybackThreadFromTrackSelect]) extends GsCommand
final case class StopPlaybackThread(replyTo: ActorRef[RespondStopPlaybackThread]) extends GsCommand
final case class RespondStopPlaybackThread(replyTo: ActorRef[StopPlaybackThread]) extends GsCommand

// GsPlayback
final case class RespondPlayTrig(replyTo: ActorRef[PlayTrig | PlayFromTrackSelectTrig | ReadPlaybackThreadState]) extends GsCommand
final case class RespondPlayFromTrackSelectTrig(replyTo: ActorRef[PlayFromTrackSelectTrig | ReadPlaybackThreadState]) extends GsCommand
final case class RespondPlayFromNextOrPrevTrack(replyTo: ActorRef[PlayFromNextOrPrevTrack | ReadPlaybackThreadState]) extends GsCommand
final case class RespondPauseTrig(replyTo: ActorRef[PauseTrig]) extends GsCommand
final case class RespondStopTrig(replyTo: ActorRef[StopTrig]) extends GsCommand
final case class RespondSetPlaybackSpeed(replyTo: ActorRef[SetPlaybackSpeed]) extends GsCommand

// GsTransportControl
final case class PlayTrig(replyTo: ActorRef[RespondPlayTrig]) extends GsCommand
final case class PlayFromTrackSelectTrig(path: String, replyTo: ActorRef[RespondPlayFromTrackSelectTrig]) extends GsCommand
final case class PlayFromNextOrPrevTrack(path: String, replyTo: ActorRef[RespondPlayFromNextOrPrevTrack]) extends GsCommand
final case class PauseTrig(replyTo: ActorRef[RespondPauseTrig]) extends GsCommand
final case class StopTrig(replyTo: ActorRef[RespondStopTrig]) extends GsCommand
final case class SetPlaybackSpeed(speed: GsPlaybackSpeed, replyTo: ActorRef[RespondSetPlaybackSpeed]) extends GsCommand

// GsRestController
final case class HydrateStateToDisplay() extends GsCommand

// GsAppStateManager
final case class HydrateState(appStateJson: String, replyTo: ActorRef[RespondHydrateState]) extends GsCommand
final case class RespondHydrateState(replyTo: ActorRef[HydrateState]) extends GsCommand

final case class AddTrackToPlaylist(track: Track, replyTo: ActorRef[RespondAddTrackToPlaylist]) extends GsCommand
final case class RespondAddTrackToPlaylist(replyTo: ActorRef[AddTrackToPlaylist]) extends GsCommand

final case class ClearPlaylist(replyTo: ActorRef[RespondClearPlaylist]) extends GsCommand
final case class RespondClearPlaylist(replyTo: ActorRef[ClearPlaylist]) extends GsCommand

final case class CurrPlaylistTrackIdx(newIdx: Int, replyTo: ActorRef[RespondCurrPlaylistTrackIdx]) extends GsCommand
final case class RespondCurrPlaylistTrackIdx(replyTo: ActorRef[CurrPlaylistTrackIdx]) extends GsCommand

final case class SetPlaylist(playlist: Playlist, replyTo: ActorRef[RespondSetPlaylist]) extends GsCommand
final case class RespondSetPlaylist(replyTo: ActorRef[SetPlaylist]) extends GsCommand

final case class NextTrack() extends GsCommand
final case class PrevTrack() extends GsCommand

final case class TransportTrig(newPlayState: GsPlayState) extends GsCommand

final case class TimerStart(ms: Int, replyTo: ActorRef[RespondTimerStart]) extends GsCommand
final case class RespondTimerStart(id: String, replyTo: ActorRef[TimerStart]) extends GsCommand

final case class SendReadComplete() extends GsCommand
final case class SendLastFrameId(lastFrameId: Long) extends GsCommand