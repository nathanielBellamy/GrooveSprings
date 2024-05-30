package dev.nateschieber.groovesprings.rest.responses.artist;

import dev.nateschieber.groovesprings.entities.Artist;
import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistByAlbumIdsDto;
import java.util.List;

public class ArtistGetByAlbumIdsResponse extends ArtistResponse {
  public ArtistGetByAlbumIdsResponse(List<Artist> artists, List<Long> albumIds) {
    this.setData( new ArtistByAlbumIdsDto(artists, artists.size(), albumIds));
  }
}
