package dev.nateschieber.groovesprings.rest.responses.priced.pricedTrack;

import dev.nateschieber.groovesprings.price.pricedEntities.PricedTrack;
import dev.nateschieber.groovesprings.rest.dtos.priced.track.PricedTrackEntityDto;

public class PricedTrackEntityResponse extends PricedTrackResponse {
  public PricedTrackEntityResponse(PricedTrack pricedTrack) {
    this.setData(new PricedTrackEntityDto(pricedTrack));
  }
}
