
export enum PlaybackActionTypes {
  AddTrackToPlaylist = "[Playback] Add Track To Playlist",
  ClearPlaylist = "[Playback] Clear Playlist",
  SetCurrPlaylistTrackIdx = "[Playback] Set Curr Playlist Track Idx",
  SetCurrFile = "[Playback] Set Curr File",
  SetCurrFileSuccess = "[Playback] Set Curr File Success",
  SetCurrFileFailure = "[Playback] Set Curr File Failure",

  SetCurrTrack = "[Playback] Set Curr Track",
  UpdateCurrPlaylistTrackIdx = "[Playback] Update Curr Playlist Track Idx",

  SetPlaylistAsCurr = "[Playback] Set Playlist As Curr",
  SetPlaylistAsCurrSuccess = "[Playback] Set Playlist As Curr Success",
  SetPlaylistAsCurrFailure = "[Playback] Set Playlist As Curr Failure",

  FetchAppState = "[Playback] Fetch App State",
  FetchAppStateSuccess = "[Playback] Fetch App State Success",
  FetchAppStateFailure = "[Playback] Fetch App State Failure",
  SetPlaybackState = "[Playback] Set Playback State",
  CachePlaybackState = "[Playback] Cache Playback State",

  PlayTrig = "[Playback] Play Trig",
  PauseTrig = "[Playback] Pause Trig",
  StopTrig = "[Playback] Stop Trig",
  PlaybackSpeedTrig = "[Playback] Playback Speed Trig",
  SetLoopType = "[Playback] Set Loop Type",
  ToggleShuffle = "[Playback] ToggleShuffle",

  NextTrackTrig = "[Playback] Next Track Trig",
  PrevTrackTrig = "[Playback] Prev Track Trig",

  HydrateAppState = "[Playback] Hydrate App State",
}
