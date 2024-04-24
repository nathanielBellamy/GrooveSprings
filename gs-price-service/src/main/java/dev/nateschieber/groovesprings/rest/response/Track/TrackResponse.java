package dev.nateschieber.groovesprings.rest.response.Track;

import dev.nateschieber.groovesprings.rest.dto.Track.TrackDto;
import dev.nateschieber.groovesprings.rest.responses.GsHttpResponse;

public abstract class TrackResponse extends GsHttpResponse {

  private TrackDto data;

  public TrackDto getData() {
    return data;
  }

  public void setData(TrackDto data) {
    this.data = data;
  }
}
