package dev.nateschieber.groovesprings.rest.responses.Album;

import dev.nateschieber.groovesprings.entities.Price;
import dev.nateschieber.groovesprings.rest.dtos.Album.AlbumPriceDto;

public class AlbumPriceResponse extends AlbumResponse {
  public AlbumPriceResponse(Price price) {
    this.setData( new AlbumPriceDto(price));
  }
}
