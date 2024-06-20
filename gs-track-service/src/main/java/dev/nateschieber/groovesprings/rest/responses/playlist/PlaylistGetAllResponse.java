package dev.nateschieber.groovesprings.rest.responses.playlist;

import dev.nateschieber.groovesprings.entities.Playlist;
import dev.nateschieber.groovesprings.rest.dtos.playlist.PlaylistGetAllDto;
import java.util.List;

public class PlaylistGetAllResponse extends PlaylistResponse {
  public PlaylistGetAllResponse(List<Playlist> playlists) {
    this.setData(new PlaylistGetAllDto(playlists.size(), playlists));
  }
}
