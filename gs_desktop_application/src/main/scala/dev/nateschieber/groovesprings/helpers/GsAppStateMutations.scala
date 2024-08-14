package dev.nateschieber.groovesprings.helpers

import dev.nateschieber.groovesprings.entities.{AppState, EmptyPlaylist, EmptyTrack, Playlist, Track}
import dev.nateschieber.groovesprings.enums.{GsLoopType, GsPlayState, GsPlaybackSpeed}

import scala.util.Random

class GsAppStateMutations {

  def clearPlaylist(appState: AppState): AppState = {
    AppState(
      appState.playState,
      appState.playbackSpeed,
      appState.loopType,
      appState.shuffle,
      appState.currFrameId,
      appState.currTrack,
      appState.currPlaylistTrackIdx,
      EmptyPlaylist
    )
  }

  def setCurrPlaylistTrackIdx(appState: AppState, newIdx: Int): AppState = {
    val optTrack = appState.playlist.tracks.lift(newIdx)
    if (optTrack.isDefined)
      AppState(
        appState.playState,
        appState.playbackSpeed,
        appState.loopType,
        appState.shuffle,
        appState.currFrameId,
        optTrack.get,
        newIdx,
        appState.playlist)
    else
      AppState(
        appState.playState,
        appState.playbackSpeed,
        appState.loopType,
        appState.shuffle,
        appState.currFrameId,
        EmptyTrack,
        0,
        appState.playlist
      )
  }

  def setLoopType(state: AppState): AppState = {
    val newLoopType = state.loopType match {
      case GsLoopType.ONE  => GsLoopType.NONE
      case GsLoopType.ALL  => GsLoopType.ONE
      case GsLoopType.NONE => GsLoopType.ALL
    }

    AppState(
      state.playState,
      state.playbackSpeed,
      newLoopType,
      state.shuffle,
      state.currFrameId,
      state.currTrack,
      state.currPlaylistTrackIdx,
      state.playlist
    )
  }

  def setCurrFrameId(appState: AppState, newCurrFrameId: Long): AppState = {
    AppState(
      appState.playState,
      appState.playbackSpeed,
      appState.loopType,
      appState.shuffle,
      newCurrFrameId,
      appState.currTrack,
      appState.currPlaylistTrackIdx,
      appState.playlist
    )
  }

  private def newRandomIdx(playlistLength: Int, oldIdx: Int): Int = {
    var randomIdx: Int = Random.between(0, playlistLength)
    while
      randomIdx == oldIdx
    do randomIdx = newRandomIdx(playlistLength, oldIdx)
    randomIdx
  }

  // TODO:
  //   - set new currFrameId based on playbackSpeed when moving between tracks
  def prevTrack(appState: AppState): AppState = {
    if (!(appState.playlist.tracks.map(track => track.id) contains appState.currTrack.id))
      if (appState.loopType != GsLoopType.NONE)
        return appState
      else
        return AppState(
          GsPlayState.STOP,
          appState.playbackSpeed,
          appState.loopType,
          appState.shuffle,
          0,
          appState.currTrack,
          0,
          appState.playlist
        )

    val playlistLength = appState.playlist.tracks.length
    if (playlistLength == 0)
      return AppState(
        appState.playState,
        appState.playbackSpeed,
        appState.loopType,
        appState.shuffle,
        0,
        appState.currTrack,
        0,
        EmptyPlaylist
      )

    val oldIdx = appState.currPlaylistTrackIdx
    val newIdx = if (appState.loopType == GsLoopType.ONE)
      oldIdx
    else if (appState.shuffle)
      newRandomIdx(appState.playlist.tracks.length, oldIdx)
    else
      (oldIdx + (playlistLength - 1)) % playlistLength

    AppState(
      appState.playState,
      appState.playbackSpeed,
      appState.loopType,
      appState.shuffle,
      0,
      appState.playlist.tracks(newIdx),
      newIdx,
      appState.playlist
    )
  }

  def nextTrack(appState: AppState): AppState = {
    if (!(appState.playlist.tracks.map(track => track.id) contains appState.currTrack.id))
      if (appState.loopType != GsLoopType.NONE)
        return appState
      else
        return AppState(
          GsPlayState.STOP,
          appState.playbackSpeed,
          appState.loopType,
          appState.shuffle,
          0,
          appState.currTrack,
          0,
          appState.playlist
        )

    if (appState.playlist.tracks.isEmpty)
      return AppState(
        appState.playState,
        appState.playbackSpeed,
        appState.loopType,
        appState.shuffle,
        0,
        appState.currTrack,
        0,
        EmptyPlaylist
      )

    val oldIdx = appState.currPlaylistTrackIdx
    // loop behavior
    if (appState.loopType == GsLoopType.NONE
      && oldIdx == appState.playlist.tracks.length - 1)
      return AppState(
        GsPlayState.STOP,
        appState.playbackSpeed,
        appState.loopType,
        appState.shuffle,
        0,
        appState.playlist.tracks.head,
        0,
        appState.playlist
      )

    val newIdx = if (appState.loopType == GsLoopType.ONE)
      oldIdx
    else if (appState.shuffle)
      newRandomIdx(appState.playlist.tracks.length, oldIdx)
    else
      (oldIdx + 1) % appState.playlist.tracks.length

    AppState(
      appState.playState,
      appState.playbackSpeed,
      appState.loopType,
      appState.shuffle,
      0,
      appState.playlist.tracks(newIdx),
      newIdx,
      appState.playlist
    )
  }

  def setPlayState(appState: AppState, newPlayState: GsPlayState): AppState = {
    AppState(
      newPlayState,
      appState.playbackSpeed,
      appState.loopType,
      appState.shuffle,
      appState.currFrameId,
      appState.currTrack,
      appState.currPlaylistTrackIdx,
      appState.playlist
    )
  }

  def setPlaybackSpeed(state: AppState, newPlaybackSpeed: GsPlaybackSpeed): AppState = {
    AppState(
      state.playState,
      newPlaybackSpeed,
      state.loopType,
      state.shuffle,
      state.currFrameId,
      state.currTrack,
      state.currPlaylistTrackIdx,
      state.playlist
    )
  }

  def setShuffle(state: AppState): AppState = {
    AppState(
      state.playState,
      state.playbackSpeed,
      state.loopType,
      !state.shuffle,
      state.currFrameId,
      state.currTrack,
      state.currPlaylistTrackIdx,
      state.playlist
    )
  }

  def setCurrTrack(appState: AppState, track: Track): AppState = {
    AppState(
      appState.playState,
      appState.playbackSpeed,
      appState.loopType,
      appState.shuffle,
      appState.currFrameId,
      track,
      appState.currPlaylistTrackIdx,
      appState.playlist
    )
  }

  def addTrackToPlaylist(appState: AppState, track: Track): AppState = {
    AppState(
      appState.playState,
      appState.playbackSpeed,
      appState.loopType,
      appState.shuffle,
      appState.currFrameId,
      appState.currTrack,
      appState.currPlaylistTrackIdx,
      Playlist(appState.playlist.id, appState.playlist.name, appState.playlist.tracks ++ List(track))
    )
  }

  def setPlaylist(appState: AppState, playlist: Playlist): AppState = {
    AppState(
      appState.playState,
      appState.playbackSpeed,
      appState.loopType,
      appState.shuffle,
      appState.currFrameId,
      appState.currTrack,
      0,
      playlist
    )
  }
}
