package dev.nateschieber.groovesprings.actors

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ws.{BinaryMessage, Message, TextMessage}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives.*
import akka.stream.scaladsl.Flow
import dev.nateschieber.groovesprings.enums.GsHttpPort
import dev.nateschieber.groovesprings.traits.{GsCommand, PlayTrig, RespondFastForwardTrig, RespondPauseTrig, RespondPlayTrig, RespondRewindTrig, RespondStopTrig, StopTrig}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

// GsTransportControl
//  - establish a websocket to handle real-time controls from frontend
//  - Frontend <-> GsTransportControl -> GsPlayback -> GsDisplay -> Frontend

object GsTransportControl {

  val GsTransportControlServiceKey = ServiceKey[GsCommand]("gs_transport_control")

  def apply(gsPlaybackRef: ActorRef[GsCommand], gsDisplayRef: ActorRef[GsCommand]): Behavior[GsCommand] = Behaviors.setup {
    context =>
      context.system.receptionist ! Receptionist.Register(GsTransportControlServiceKey, context.self)

      given system: ActorSystem[Nothing] = context.system

      val gsTransportControl = new GsTransportControl(context, gsPlaybackRef, gsDisplayRef)

      lazy val server = Http()
        .newServerAt("localhost", GsHttpPort.GsTransportControl.port)
        .adaptSettings(_.mapWebsocketSettings(_.withPeriodicKeepAliveMaxIdle(Duration("10 minutes"))))
        .bind(gsTransportControl.route)

      server.map { _ =>
        println("GsTransportControlServer online at localhost:" + GsHttpPort.GsTransportControl.port)
      } recover { case ex =>
        println(ex.getMessage)
      }

      gsTransportControl
  }
}

class GsTransportControl(context: ActorContext[GsCommand], gsPlaybackRef: ActorRef[GsCommand], gsDisplayRef: ActorRef[GsCommand]) extends AbstractBehavior[GsCommand](context) {
  
  private val playbackRef: ActorRef[GsCommand] = gsPlaybackRef
  private val displayRef: ActorRef[GsCommand] = gsDisplayRef

  val route: Route =
    path( "gs-transport-control") {
      get {
        handleWebSocketMessages(websocketFlow)
      }
    }

  def websocketFlow: Flow[Message, Message, Any] =
    Flow[Message].map {
      case TextMessage.Strict(msg) =>
        msg match {
          case "play" =>
            playbackRef ! PlayTrig(displayRef)
            TextMessage.Strict("OK - PLAY")
          case "stop" =>
            playbackRef ! StopTrig(displayRef)
            TextMessage.Strict("OK - STOP")
          case default => TextMessage.Strict("BAD MESSAGE")
        }
      case BinaryMessage.Strict(b) => TextMessage.Strict("Ok - ws BinaryMessage")
    }

  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    msg match {
      case RespondPlayTrig(replyTo) =>
        println("GsTransportControl :: respondPlayTrig")
        Behaviors.same

      case RespondPauseTrig(replyTo) =>
        println("GsTransportControl :: respondPauseTrig")
        Behaviors.same

      case RespondStopTrig(replyTo) =>
        println("GsTransportControl :: respondStopTrig")
        Behaviors.same

      case RespondFastForwardTrig(replyTo) =>
        println("GsTransportControl :: respondFastForwardTrig")
        Behaviors.same

      case RespondRewindTrig(replyTo) =>
        println("GsTransportControl :: respondRewindTrig")
        Behaviors.same
    }
  }
}