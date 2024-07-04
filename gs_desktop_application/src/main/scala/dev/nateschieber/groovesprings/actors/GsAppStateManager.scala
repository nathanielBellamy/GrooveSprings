package dev.nateschieber.groovesprings.actors

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.receptionist.ServiceKey
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import dev.nateschieber.groovesprings.actors.GsAppStateManager.appState
import dev.nateschieber.groovesprings.entities.{AppState, AppStateJsonSupport, EmptyAppState}
import dev.nateschieber.groovesprings.rest.{CacheStateDto, CacheStateJsonSupport, FileSelectDto, FileSelectJsonSupport, PlaybackSpeedDto, PlaybackSpeedJsonSupport}
import dev.nateschieber.groovesprings.traits.{FileSelect, GsCommand, PauseTrig, PlayTrig, SetPlaybackSpeed, StopTrig}

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths}
import scala.annotation.static

// spray + protocol import needed to call .convertTo[AppState]
import spray.json._
import dev.nateschieber.groovesprings.entities.AppStateJsonProtocol._

object GsAppStateManager {

  private val stateCacheFile: String = "__GROOVE_SPRINGS__STATE_CACHE__.json"
  private val appState: AppState = GsAppStateManager.loadAppState()

  val GsAppStateManagerServiceKey = ServiceKey[GsCommand]("gs_rest_controller")

  @static private def loadAppState(): AppState = {
    val appStatePath = Path.of(stateCacheFile)
    if (!Files.exists(appStatePath))
      return EmptyAppState
    val appStateJson = Files.readString(appStatePath, StandardCharsets.UTF_8)
    appStateJson.parseJson.convertTo[AppState]
  }

  @static private def cacheState(appState: AppState): Unit = {
    Files.write(Paths.get(stateCacheFile), appState.toJson.compactPrint.getBytes(StandardCharsets.UTF_8))
  }

  def apply(
             gsPlaybackRef: ActorRef[GsCommand],
             gsDisplayRef: ActorRef[GsCommand]
           ): Behavior[GsCommand] = Behaviors.setup {
    context =>
      given system: ActorSystem[Nothing] = context.system

      val manager = new GsAppStateManager(context, gsPlaybackRef, gsDisplayRef)
      manager.printState()
      manager
  }
}

class GsAppStateManager(
                        context: ActorContext[GsCommand],
                        gsPlaybackRef: ActorRef[GsCommand],
                        gsDisplayRef: ActorRef[GsCommand])
  extends AbstractBehavior[GsCommand](context)
    with FileSelectJsonSupport with AppStateJsonSupport {

  def printState(): Unit = {
    println(appState.toJson.prettyPrint)
  }

  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    msg match {
      case FileSelect(track, replyTo) =>
        Behaviors.same

      case default =>
        Behaviors.same
    }
  }
}
