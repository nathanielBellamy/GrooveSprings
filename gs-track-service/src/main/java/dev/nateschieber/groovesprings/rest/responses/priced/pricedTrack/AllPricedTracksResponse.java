package dev.nateschieber.groovesprings.rest.responses.priced.pricedTrack;

import dev.nateschieber.groovesprings.entities.priced.PricedTrack;
import dev.nateschieber.groovesprings.rest.dtos.priced.track.AllPricedTracksDto;
import java.util.List;

public class AllPricedTracksResponse extends PricedTrackResponse {
  public AllPricedTracksResponse(List<PricedTrack> pricedTracks) {
    this.setData(new AllPricedTracksDto(pricedTracks));
  }
}
