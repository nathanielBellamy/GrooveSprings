
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

  FetchLastState = "[Playback] Fetch Last State",
  FetchLastStateSuccess = "[Playback] Fetch Last State Success",
  FetchLastStateFailure = "[Playback] Fetch Last State Failure",
  SetPlaybackState = "[Playback] Set Playback State",
  CachePlaybackState = "[Playback] Cache Playback State",

  PlayTrig = "[Playback] Play Trig",
  PauseTrig = "[Playback] Pause Trig",
  StopTrig = "[Playback] Stop Trig",
  PlaybackSpeedTrig = "[Playback] Playback Speed Trig",

  NextTrack = "[Playback] Next Track",
}
