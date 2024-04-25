package dev.nateschieber.groovesprings.rest.responses.artist;

import dev.nateschieber.groovesprings.entities.Artist;
import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistGetAllDto;
import java.util.List;

public class ArtistGetAllResponse extends ArtistResponse {
  public ArtistGetAllResponse(List<Artist> artists) {
    this.setData(new ArtistGetAllDto(artists.size(), artists));
  }
}
