
// TODO: load from env
const apiVersionPrefix = "api/v1"
const transportPrefix = apiVersionPrefix + "/transport"

export class AppRoutesSrvr {
  static trackSelect() {
    return apiVersionPrefix + "/trackSelect"
  }

  static play() {
    return transportPrefix + "?cmd=play"
  }

  static pause() {
    return transportPrefix + "?cmd=pause"
  }

  static nextTrack() {
    return transportPrefix + "?cmd=nextTrack"
  }

  static prevTrack() {
    return transportPrefix + "?cmd=prevTrack"
  }

  static stop() {
    return transportPrefix + "?cmd=stop"
  }

  static playbackSpeed() {
    return transportPrefix + "/playbackSpeed"
  }

  static appState() {
    return apiVersionPrefix + "/appState"
  }

  static addTrackToPlaylist() {
    return apiVersionPrefix + "/addTrackToPlaylist"
  }

  static clearPlaylist() {
    return apiVersionPrefix + "/clearPlaylist"
  }

  static currPlaylistTrackIdx(newIdx: number) {
    return apiVersionPrefix + "/currPlaylistTrackIdx?newIdx=" + newIdx
  }

  static setPlaylist() {
    return apiVersionPrefix + "/setPlaylist"
  }
}
