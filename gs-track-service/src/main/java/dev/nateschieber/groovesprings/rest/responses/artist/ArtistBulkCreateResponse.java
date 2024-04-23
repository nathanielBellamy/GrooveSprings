package dev.nateschieber.groovesprings.rest.responses.artist;

import dev.nateschieber.groovesprings.entities.Artist;
import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistBulkCreateDto;
import java.util.List;

public class ArtistBulkCreateResponse extends ArtistResponse {
  public ArtistBulkCreateResponse(List<Artist> artists) {
    this.setData(new ArtistBulkCreateDto(artists));
  }
}
