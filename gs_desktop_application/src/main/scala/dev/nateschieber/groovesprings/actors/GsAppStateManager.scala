package dev.nateschieber.groovesprings.actors

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.receptionist.ServiceKey
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import dev.nateschieber.groovesprings.actors.GsAppStateManager.lastStateCacheFile
import dev.nateschieber.groovesprings.entities.{AppState, AppStateJsonSupport}
import dev.nateschieber.groovesprings.rest.{CacheStateDto, CacheStateJsonSupport, FileSelectDto, FileSelectJsonSupport, PlaybackSpeedDto, PlaybackSpeedJsonSupport}
import dev.nateschieber.groovesprings.traits.{FileSelect, GsCommand, PauseTrig, PlayTrig, SetPlaybackSpeed, StopTrig}

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths}
import scala.concurrent.ExecutionContext.Implicits.global
import spray.json.*

import scala.annotation.static

object GsAppStateManager {

  private var lastStateCacheFile: String = "__GROOVE_SPRINGS__LAST_STATE__.json"
  private var appState: AppState = GsAppStateManager.loadAppState()

  val GsAppStateManagerServiceKey = ServiceKey[GsCommand]("gs_rest_controller")

  @static def loadAppState(): AppState = {
    val appStatePath = Path.of(lastStateCacheFile)
    val appStateJson = Files.readString(appStatePath, StandardCharsets.UTF_8)
    // TODO: get AppStateJsonSupport here
    val appState: AppState = appStateJson.parseJson.convertTo[AppState]
    appState
  }

  def cacheLastState(stateJson: String): Unit = {
    Files.write(Paths.get(lastStateCacheFile), stateJson.getBytes(StandardCharsets.UTF_8))
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
