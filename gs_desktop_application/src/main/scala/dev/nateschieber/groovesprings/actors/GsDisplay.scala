package dev.nateschieber.groovesprings.actors

import akka.NotUsed
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.receptionist.Receptionist
import akka.actor.typed.receptionist.ServiceKey
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ws.{BinaryMessage, Message, TextMessage}
import akka.http.scaladsl.server.Directives.{get, handleWebSocketMessages, path}
import akka.http.scaladsl.server.Route
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Flow, Sink, Source, SourceQueueWithComplete}
import dev.nateschieber.groovesprings.traits.*
import akka.util.Timeout
import dev.nateschieber.groovesprings.enums.{GsHttpPort, GsPlayState}

import scala.concurrent.ExecutionContext.Implicits.global
import java.util.concurrent.TimeUnit
import scala.language.postfixOps

object GsDisplay {

    val GsDisplayServiceKey = ServiceKey[GsCommand]("gs_display")

    def apply(playbackRef: ActorRef[GsCommand]): Behavior[GsCommand] = Behaviors.setup {
      context =>
        context.system.receptionist ! Receptionist.Register(GsDisplayServiceKey, context.self)

        given system: ActorSystem[Nothing] = context.system

        val gsDisplay = new GsDisplay(context, playbackRef)

        lazy val server = Http()
          .newServerAt("localhost", GsHttpPort.GsDisplay.port)
          .adaptSettings(_.mapWebsocketSettings(_.withPeriodicKeepAliveMode("ping")))
          .bind(gsDisplay.route)

        server.map { _ =>
          println("GsDisplayServer online at localhost:" + GsHttpPort.GsDisplay.port)
        } recover { case ex =>
          println(ex.getMessage)
        }

        gsDisplay
    }
}

class GsDisplay(context: ActorContext[GsCommand], gsPlaybackRef: ActorRef[GsCommand]) extends AbstractBehavior[GsCommand](context) {

  implicit val timeout: Timeout = Timeout.apply(100, TimeUnit.MILLISECONDS)
  private val playbackRef: ActorRef[GsCommand] = gsPlaybackRef

  private var browserConnections: List[TextMessage => Unit] = List()

  val route: Route =
    path("gs-display") {
      get {
        handleWebSocketMessages(websocketFlow)
      }
    }

  def websocketFlow: Flow[Message, Message, Any] = {
    // based on https://github.com/JannikArndt/simple-akka-websocket-server-push/blob/master/src/main/scala/WebSocket.scala
    val inbound: Sink[Message, Any] = Sink.foreach(_ => ()) // frontend pings to keep alive, but backend does not use pings
    val outbound: Source[Message, SourceQueueWithComplete[Message]] = Source.queue[Message](16, OverflowStrategy.fail)

    Flow.fromSinkAndSourceMat(inbound, outbound)((_, outboundMat) => {
      browserConnections ::= outboundMat.offer
      NotUsed
    })
  }

  def sendWebsocketMsg(text: String): Unit = {
    for (connection <- browserConnections) connection(TextMessage.Strict(text))
  }

  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    msg match {
      case RespondPlayTrig(replyTo) =>
        replyTo ! ReadPlaybackThreadState(context.self)
        Behaviors.same

      case RespondPauseTrig(replyTo) =>
        Behaviors.same

      case RespondStopTrig(replyTo) =>
        Behaviors.same

      case RespondPlaybackThreadState(lastFrameId, playState, readComplete, replyTo) =>
        if (readComplete)
          sendWebsocketMsg("-1")
          return Behaviors.same
        sendWebsocketMsg(lastFrameId.toString)
        if (playState == GsPlayState.PLAY)
          Thread.sleep(100)
          replyTo ! ReadPlaybackThreadState(context.self)
        Behaviors.same

      case HydrateState(appStateJson, replyTo) =>
        sendWebsocketMsg(appStateJson)
        replyTo ! RespondHydrateState(context.self)
        Behaviors.same

      case InitDisplay() =>
        playbackRef ! ReadPlaybackThreadState(context.self)
        Behaviors.same
    }
  }
}


