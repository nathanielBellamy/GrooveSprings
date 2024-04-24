package dev.nateschieber.groovesprings.rest.responses.priced.pricedTrack;

import dev.nateschieber.groovesprings.rest.dtos.priced.track.PricedTrackDto;
import dev.nateschieber.groovesprings.rest.responses.GsHttpResponse;

public abstract class PricedTrackResponse extends GsHttpResponse {

  private PricedTrackDto data;

  public PricedTrackDto getData() {
    return data;
  }

  public void setData(PricedTrackDto data) {
    this.data = data;
  }
}
