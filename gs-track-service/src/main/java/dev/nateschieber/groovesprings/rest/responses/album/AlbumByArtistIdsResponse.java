package dev.nateschieber.groovesprings.rest.responses.album;

import dev.nateschieber.groovesprings.entities.Album;
import dev.nateschieber.groovesprings.rest.dtos.album.AlbumByArtistIdsDto;
import java.util.List;

public class AlbumByArtistIdsResponse extends AlbumResponse {
  public AlbumByArtistIdsResponse(List<Long> artistIds, List<Album> albums) {
    this.setData(new AlbumByArtistIdsDto(artistIds, albums.size(), albums));
  }
}
