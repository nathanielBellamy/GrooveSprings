package dev.nateschieber.groovesprings.actors

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ws.{BinaryMessage, Message, TextMessage}
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives.*
import akka.stream.scaladsl.Flow
import dev.nateschieber.groovesprings.enums.GsHttpPort
import dev.nateschieber.groovesprings.traits.{GsCommand, RespondFastForwardTrig, RespondPauseTrig, RespondPlayTrig, RespondRewindTrig, RespondStopTrig}

import scala.annotation.static
import scala.concurrent.Future

// GsTransportControl
//  - establish a websocket to handle real-time controls from frontend
//  - Frontend <-> GsTransportControl -> GsPlayback -> GsDisplay -> Frontend

object GsTransportControl {

  val GsTransportControlServiceKey = ServiceKey[GsCommand]("gs_transport_control")

  def websocketFlow: Flow[Message, Message, Any] =
    Flow[Message].map {
      case TextMessage.Strict(s) => TextMessage.Strict("Ok - ws TextMessage")
      case BinaryMessage.Strict(b) => TextMessage.Strict("Ok - ws BinaryMessage")
    }

  val route: Route =
    path( "gs-transport-control") {
      get {
        handleWebSocketMessages(websocketFlow)
      }
    }

  def apply(): Behavior[GsCommand] = Behaviors.setup {
    context =>
      context.system.receptionist ! Receptionist.Register(GsTransportControlServiceKey, context.self)

      implicit val system = context.system

      lazy val server = Http().newServerAt("localhost", GsHttpPort.GsTransportControl.port).bind(route)

      new GsTransportControl(context)
  }
}

class GsTransportControl(context: ActorContext[GsCommand]) extends AbstractBehavior[GsCommand](context) {

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
