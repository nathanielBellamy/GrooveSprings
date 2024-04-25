package dev.nateschieber.groovesprings.rest.responses.priced.pricedTrack;

import dev.nateschieber.groovesprings.price.pricedEntities.PricedTrack;
import dev.nateschieber.groovesprings.rest.dtos.priced.track.PricedTracksByReleaseYearDto;
import java.util.List;

public class PricedTracksByReleaseYearResponse extends PricedTrackResponse {
  public PricedTracksByReleaseYearResponse(int year, List<PricedTrack> pricedTracks) {
    this.setData( new PricedTracksByReleaseYearDto(year, pricedTracks.size(), pricedTracks));
  }
}
