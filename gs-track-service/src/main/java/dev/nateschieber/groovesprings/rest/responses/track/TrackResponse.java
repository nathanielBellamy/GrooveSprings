package dev.nateschieber.groovesprings.rest.responses.track;

import dev.nateschieber.groovesprings.rest.dtos.track.TrackDto;
import dev.nateschieber.groovesprings.rest.responses.GsHttpResponse;

public abstract class TrackResponse extends GsHttpResponse {
  public TrackDto getData() {
    return data;
  }

  public void setData(TrackDto data) {
    this.data = data;
  }

  private TrackDto data;
}
