package dev.nateschieber.groovesprings

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.server.Route
import dev.nateschieber.groovesprings.actors.GsSupervisor
import dev.nateschieber.groovesprings.enums.GsHttpPort
import dev.nateschieber.groovesprings.jni.JniMain

import java.awt.Desktop
import java.net.URI
import scala.annotation.static
import scala.concurrent.ExecutionContext.Implicits.global


  @main def GsDesktopApplication(): Unit = {
    val vst3HostAppPtr: Long = JniMain.allocVst3Host()
    JniMain.initVst3Host(vst3HostAppPtr)

    given system: ActorSystem[Nothing] = ActorSystem(GsSupervisor(1l), "gs_desktop_application")

    lazy val server = Http().newServerAt("localhost", GsHttpPort.GsDesktopApplication.port).bind(routes())

    server.map(_ => {
      //
    })

    if (Desktop.isDesktopSupported && Desktop.getDesktop.isSupported(Desktop.Action.BROWSE))
      Desktop.getDesktop.browse(new URI("http://localhost:" + GsHttpPort.GsRestController.port))
  }

  def routes(): Route = {
    concat(
      path("api" / "v1" / "hello") {
        get {
          complete("Welcome to Groove Springs.")
        }
      }
    )
  }