package dev.nateschieber.groovesprings

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.server.Route
import dev.nateschieber.groovesprings.jni.JniMain

import scala.concurrent.ExecutionContext.Implicits.global

object DesktopApplication {

  def simonSays(x : Array[String]): String = x.foldLeft("Simon Says: ")((a,b) => a + " " + b)

  @main def main(): Unit =
    println(JniMain.main())
    given system: ActorSystem = ActorSystem("gs-desktop-app")
    val route = path("foo") {
      get {
        complete("Hello World! Your friend, Akka. ")
      }
    }
    val server = Http().newServerAt("localhost", 8081).bind(route)
    server.map { _ =>
      println("Server online at http://localhost:8081")
    } recover { case ex =>
      println("Server could not start: ${ex.getMessage}")
    }
}
