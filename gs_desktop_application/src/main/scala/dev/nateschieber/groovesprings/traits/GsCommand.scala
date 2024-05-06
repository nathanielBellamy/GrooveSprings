package dev.nateschieber.groovesprings.traits

import akka.actor.typed.ActorRef

sealed trait GsCommand

final case class ReadFrameId(replyTo: ActorRef[RespondFrameId]) extends GsCommand
final case class RespondFrameId(value: Integer, replyTo: ActorRef[ReadFrameId]) extends GsCommand

final case class InitDisplay(playbackRef: ActorRef[ReadFrameId]) extends GsCommand

