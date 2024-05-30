package dev.nateschieber.groovesprings.rest.responses.track;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackByAlbumIdsDto;
import java.util.List;

public class TracksByAlbumIdsResponse extends TrackResponse {
  public TracksByAlbumIdsResponse(List<Track> tracks, List<Long> albumIds) {
    this.setData( new TrackByAlbumIdsDto(tracks, tracks.size(), albumIds));
  }
}
