package dev.nateschieber.groovesprings.rest.responses.album;

import dev.nateschieber.groovesprings.entities.Album;
import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.rest.dtos.album.AlbumTracksDto;
import java.util.List;

public class AlbumTracksResponse extends AlbumResponse {
  public AlbumTracksResponse(Album album, List<Track> tracks) {
    this.setData(new AlbumTracksDto(album, tracks.size(), tracks));
  }
}
