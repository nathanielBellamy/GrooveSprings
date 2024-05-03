package dev.nateschieber.groovesprings

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.server.Route
import dev.nateschieber.groovesprings.jni.JniMain

import scala.concurrent.ExecutionContext.Implicits.global

object GsDesktopApplication {

  @main def main(): Unit = {
    println(JniMain.main())
    given system: ActorSystem = ActorSystem("gs_desktop_application")

    lazy val server = Http().newServerAt("localhost", 8765).bind(routes())

    server.map { _ =>
      println("Server online at http://localhost:8765")
    } recover { case ex =>
      println(ex.getMessage)
    }
  }

  private def routes(): Route = {
    val apiPrefix: String = "api/v1"
    concat(
      path("hello") {
        get {
          complete("Hello World! Your friend, Akka. ")
        }
      },
      path("add") {
        get {
          parameters(Symbol("x").as[Int], Symbol("y").as[Int]) { (x: Int, y: Int) => {
            complete("result: " + JniMain.add(x, y))
          }}
        }
      }
    )
  }
}