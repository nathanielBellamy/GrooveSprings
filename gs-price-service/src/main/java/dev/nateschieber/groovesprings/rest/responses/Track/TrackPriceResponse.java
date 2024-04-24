package dev.nateschieber.groovesprings.rest.responses.Track;


import dev.nateschieber.groovesprings.entities.Price;
import dev.nateschieber.groovesprings.rest.dtos.Track.TrackPriceDto;

public class TrackPriceResponse extends TrackResponse {
  public TrackPriceResponse(Price trackPrice) {
    this.setData( new TrackPriceDto(trackPrice));
  }
}
