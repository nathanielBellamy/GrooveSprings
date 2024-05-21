package dev.nateschieber.groovesprings.actors

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.receptionist.ServiceKey
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{path, *}
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
      path("api" / "v1" / "hello") {
        get {
          complete("Hello World! Your friend, Akka. ")
        }
      },
      path("api" / "v1" / "add") {
        get {
          parameters(Symbol("x").as[Int], Symbol("y").as[Int]) { (x: Int, y: Int) => {
            complete("result: " + JniMain.add(x, y))
          }}
        }
      },
      path("api" / "v1" / "file") {
        get {
          parameters(Symbol("filename").as[String], Symbol("audiocodec").as[String]) { (fileName: String, audioCodec: String) => {
            val msg = s"fileName: ${fileName} -- audioCodec: ${audioCodec}"
            complete(HttpEntity(ContentTypes.`application/json`, s"{\"msg\":\"${msg}\"}"))
          }}
        }
      },
      path("") { //the same prefix must be set as base href in index.html
        getFromResource("frontend-dist/browser/index.html")
      } ~ pathPrefix("") {
        getFromResourceDirectory("frontend-dist/browser/") ~
          getFromResource("frontend-dist/browser/index.html")
      },
    )
  }


  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    Behaviors.same
  }
}
