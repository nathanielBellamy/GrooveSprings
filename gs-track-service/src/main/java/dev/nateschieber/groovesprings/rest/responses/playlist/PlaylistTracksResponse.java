package dev.nateschieber.groovesprings.rest.responses.playlist;

import dev.nateschieber.groovesprings.entities.Playlist;
import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.rest.dtos.playlist.PlaylistTracksDto;
import java.util.List;

public class PlaylistTracksResponse extends PlaylistResponse {
  public PlaylistTracksResponse(Playlist playlist) {
    List<Track> tracks = playlist.getTracks();
    this.setData(new PlaylistTracksDto(playlist, tracks.size(), tracks));
  }
}
