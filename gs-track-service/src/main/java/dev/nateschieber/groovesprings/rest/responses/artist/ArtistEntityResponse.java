package dev.nateschieber.groovesprings.rest.responses.artist;

import dev.nateschieber.groovesprings.entities.Artist;
import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistEntityDto;

public class ArtistEntityResponse extends ArtistResponse {
  public ArtistEntityResponse(Artist artist) {
    this.setData(new ArtistEntityDto(artist));
  }
}
