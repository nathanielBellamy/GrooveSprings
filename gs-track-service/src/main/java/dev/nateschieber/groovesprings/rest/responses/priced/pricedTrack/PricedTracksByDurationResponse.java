package dev.nateschieber.groovesprings.rest.responses.priced.pricedTrack;

import dev.nateschieber.groovesprings.price.pricedEntities.PricedTrack;
import dev.nateschieber.groovesprings.rest.dtos.priced.track.PricedTracksByDurationDto;
import java.util.List;

public class PricedTracksByDurationResponse extends PricedTrackResponse {
  public PricedTracksByDurationResponse(Long min, Long max, List<PricedTrack> pricedTracks) {
    this.setData( new PricedTracksByDurationDto(min, max, pricedTracks.size(), pricedTracks));
  }
}
