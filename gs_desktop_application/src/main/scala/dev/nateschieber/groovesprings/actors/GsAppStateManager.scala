package dev.nateschieber.groovesprings.actors

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.receptionist.ServiceKey
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import dev.nateschieber.groovesprings.actors.GsAppStateManager.lastStateCacheFile
import dev.nateschieber.groovesprings.entities.{AppState, AppStateJsonSupport, EmptyAppState}
import dev.nateschieber.groovesprings.rest.{CacheStateDto, CacheStateJsonSupport, FileSelectDto, FileSelectJsonSupport, PlaybackSpeedDto, PlaybackSpeedJsonSupport}
import dev.nateschieber.groovesprings.traits.{FileSelect, GsCommand, PauseTrig, PlayTrig, SetPlaybackSpeed, StopTrig}

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths}
import scala.concurrent.ExecutionContext.Implicits.global
import spray.json.*

import scala.annotation.static

// protocol import needed to call .convertTo[AppState]
import dev.nateschieber.groovesprings.entities.AppStateJsonProtocol._

object GsAppStateManager {

  private var lastStateCacheFile: String = "__GROOVE_SPRINGS__LAST_STATE__.json"
  private var appState: AppState = GsAppStateManager.loadAppState()

  val GsAppStateManagerServiceKey = ServiceKey[GsCommand]("gs_rest_controller")

  @static private def loadAppState(): AppState = {
    val appStatePath = Path.of(lastStateCacheFile)
    if (!Files.exists(appStatePath))
      return EmptyAppState
    val appStateJson = Files.readString(appStatePath, StandardCharsets.UTF_8)
    appStateJson.parseJson.convertTo[AppState]
  }

  @static private def cacheState(appState: AppState): Unit = {
    Files.write(Paths.get(lastStateCacheFile), appState.toJson.compactPrint.getBytes(StandardCharsets.UTF_8))
  }

  private def printState(): Unit = {
    println(appState.toJson.prettyPrint)
  }

  def apply(
             gsPlaybackRef: ActorRef[GsCommand],
             gsDisplayRef: ActorRef[GsCommand]
           ): Behavior[GsCommand] = Behaviors.setup {
    context =>
      given system: ActorSystem[Nothing] = context.system

      return new GsAppStateManager(context, gsPlaybackRef, gsDisplayRef)
  }
}

class GsAppStateManager(
                        context: ActorContext[GsCommand],
                        gsPlaybackRef: ActorRef[GsCommand],
                        gsDisplayRef: ActorRef[GsCommand])
  extends AbstractBehavior[GsCommand](context)
    with FileSelectJsonSupport with AppStateJsonSupport {

  override def onMessage(msg: GsCommand): Behavior[GsCommand] = {
    msg match {
      case FileSelect(track, replyTo) =>

        Behaviors.same
    }
  }
}
