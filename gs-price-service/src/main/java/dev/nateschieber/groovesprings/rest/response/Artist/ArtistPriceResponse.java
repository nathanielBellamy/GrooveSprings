package dev.nateschieber.groovesprings.rest.response.Artist;

import dev.nateschieber.groovesprings.entities.Price;
import dev.nateschieber.groovesprings.rest.dto.Artist.ArtistPriceDto;

public class ArtistPriceResponse extends ArtistResponse{
  public ArtistPriceResponse(Price price) {
    this.setData( new ArtistPriceDto(price));
  }
}
