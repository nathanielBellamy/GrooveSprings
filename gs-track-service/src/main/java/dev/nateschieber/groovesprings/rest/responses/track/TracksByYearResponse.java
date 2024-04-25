package dev.nateschieber.groovesprings.rest.responses.track;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.rest.dtos.track.TracksByYearDto;
import java.util.List;

public class TracksByYearResponse extends TrackResponse {
  public TracksByYearResponse(Integer year, List<Track> tracks) {
    this.setData( new TracksByYearDto(year, tracks.size(), tracks) );
  }
}
