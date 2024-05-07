package dev.nateschieber.groovesprings.traits

import akka.actor.typed.ActorRef

sealed trait GsCommand


// GsDisplay
final case class InitDisplay(playbackRef: ActorRef[ReadFrameId]) extends GsCommand

// GsPlayback
final case class ReadFrameId(replyTo: ActorRef[RespondFrameId]) extends GsCommand
final case class RespondFrameId(value: Integer, replyTo: ActorRef[ReadFrameId]) extends GsCommand

final case class RespondPlayTrig(replyTo: ActorRef[PlayTrig]) extends GsCommand
final case class RespondPauseTrig(replyTo: ActorRef[PauseTrig]) extends GsCommand
final case class RespondStopTrig(replyTo: ActorRef[StopTrig]) extends GsCommand
final case class RespondFastForwardTrig(replyTo: ActorRef[FastForwardTrig]) extends GsCommand
final case class RespondRewindTrig(replyTo: ActorRef[RewindTrig]) extends GsCommand

// GsTransportControl
final case class PlayTrig(replyTo: ActorRef[RespondPlayTrig]) extends GsCommand
final case class PauseTrig(replyTo: ActorRef[RespondPauseTrig]) extends GsCommand
final case class StopTrig(replyTo: ActorRef[RespondStopTrig]) extends GsCommand
final case class FastForwardTrig(replyTo: ActorRef[RespondFastForwardTrig]) extends GsCommand
final case class RewindTrig(replyTo: ActorRef[RespondRewindTrig]) extends GsCommand
