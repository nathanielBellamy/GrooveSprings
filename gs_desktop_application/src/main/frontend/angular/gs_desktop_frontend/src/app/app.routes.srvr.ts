
// TODO: load from env
const apiVersionPrefix = "api/v1"
const transportPrefix = apiVersionPrefix + "/transport"

export class AppRoutesSrvr {
  static trackSelect() {
    return apiVersionPrefix + "/trackSelect"
  }

  static play() {
    return transportPrefix + "/play"
  }

  static pause() {
    return transportPrefix + "/pause"
  }

  static stop() {
    return transportPrefix + "/stop"
  }

  static playbackSpeed() {
    return transportPrefix + "/playbackSpeed"
  }
}
