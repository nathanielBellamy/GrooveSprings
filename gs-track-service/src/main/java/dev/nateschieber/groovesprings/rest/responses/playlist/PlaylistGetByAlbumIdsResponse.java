package dev.nateschieber.groovesprings.rest.responses.playlist;

import dev.nateschieber.groovesprings.entities.Playlist;
import dev.nateschieber.groovesprings.rest.dtos.playlist.PlaylistByAlbumIdsDto;
import java.util.List;

public class PlaylistGetByAlbumIdsResponse extends PlaylistResponse {
  public PlaylistGetByAlbumIdsResponse(List<Long> albumIds, List<Playlist> playlists) {
    this.setData(new PlaylistByAlbumIdsDto(albumIds, playlists.size(), playlists));
  }
}
