package dev.nateschieber.groovesprings.rest.responses.artist;

import dev.nateschieber.groovesprings.entities.Price;
import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistPriceDto;

public class ArtistPriceResponse extends ArtistResponse{
  public ArtistPriceResponse(Price price) {
    this.setData( new ArtistPriceDto(price));
  }
}
