package dev.nateschieber.groovesprings.rest.responses.track;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.rest.dtos.track.TracksByArtistIdsDto;
import java.util.List;

public class TracksByArtistIdsResponse extends TrackResponse {
  public TracksByArtistIdsResponse(List<Long> artistIds, List<Track> tracks) {
    this.setData(new TracksByArtistIdsDto(artistIds, tracks.size(), tracks));
  }
}
