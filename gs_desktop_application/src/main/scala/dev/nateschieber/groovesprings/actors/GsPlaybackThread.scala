//package dev.nateschieber.groovesprings.actors
//
//import akka.actor.typed.ActorRef
//import akka.actor.typed.Behavior
//import akka.actor.typed.PostStop
//import akka.actor.typed.Signal
//import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
//import akka.actor.typed.scaladsl.AbstractBehavior
//import akka.actor.typed.scaladsl.ActorContext
//import akka.actor.typed.scaladsl.Behaviors
//import dev.nateschieber.groovesprings.jni.JniMain
//import dev.nateschieber.groovesprings.traits.*
//
//object GsPlaybackThread {
//
//  val GsPlaybackThreadServiceKey = ServiceKey[GsCommand]("gs_playback")
//
//  def apply(): Behavior[GsCommand] = Behaviors.setup {
//    context =>
//      context.system.receptionist ! Receptionist.Register(GsPlaybackThreadServiceKey, context.self)
//      new GsPlaybackThread(context)
//  }
//}
//
//class GsPlaybackThread(context: ActorContext[GsCommand]) extends AbstractBehavior[GsCommand](context) {
