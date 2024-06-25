package dev.nateschieber.groovesprings.rest.responses.playlist;

import dev.nateschieber.groovesprings.entities.Playlist;
import dev.nateschieber.groovesprings.rest.dtos.playlist.PlaylistByArtistIdsDto;
import java.util.List;

public class PlaylistGetByArtistIdsResponse extends PlaylistResponse {
  public PlaylistGetByArtistIdsResponse(List<Long> artistIds, List<Playlist> playlists) {
    this.setData(new PlaylistByArtistIdsDto(artistIds, playlists.size(), playlists));
  }
}
