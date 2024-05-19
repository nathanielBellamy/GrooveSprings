package dev.nateschieber.groovesprings.actors

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.receptionist.ServiceKey
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.server.Route
import dev.nateschieber.groovesprings.enums.GsHttpPort
import dev.nateschieber.groovesprings.jni.JniMain
import dev.nateschieber.groovesprings.traits.GsCommand

import scala.concurrent.ExecutionContext.Implicits.global

object GsRestController {

  val GsRestControllerServiceKey = ServiceKey[GsCommand]("gs_rest_controller")

  def apply(gsPlaybackRef: ActorRef[GsCommand]): Behavior[GsCommand] = Behaviors.setup {
    context =>
      given system: ActorSystem[Nothing] = context.system

      val gsRestController = new GsRestController(context, gsPlaybackRef)

      lazy val server = Http()
        .newServerAt("localhost", GsHttpPort.GsRestController.port)
        .bind(gsRestController.routes())

      server.map { _ =>
        println("GsRestControllerServer online at localhost:" + GsHttpPort.GsRestController.port)
      } recover { case ex =>
        println(ex.getMessage)
      }

      gsRestController
  }
}


class GsRestController(context: ActorContext[GsCommand], gsPlaybackRef: ActorRef[GsCommand]) extends AbstractBehavior[GsCommand](context) {

  def routes(): Route = {
    concat(
      path("api" / "v1" / "file-select") {
        get {
          parameters(Symbol("filename").as[String], Symbol("audiocodec").as[String]) { (fileName, audioCodec) => {
            complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "filename: ${fileName} -- audioCodec: ${audioCodec}"))
          }}
        }
      }
    )
  }


  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    Behaviors.same
  }
}
