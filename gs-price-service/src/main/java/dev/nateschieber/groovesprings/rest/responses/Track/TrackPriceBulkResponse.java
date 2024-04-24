package dev.nateschieber.groovesprings.rest.responses.Track;

import dev.nateschieber.groovesprings.entities.Price;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackPriceBulkDto;
import java.util.List;

public class TrackPriceBulkResponse extends TrackResponse {
  public TrackPriceBulkResponse(List<Price> prices) {
    this.setData(new TrackPriceBulkDto(prices));
  }
}
