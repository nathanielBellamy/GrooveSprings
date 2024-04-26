package dev.nateschieber.groovesprings.rest.responses.track;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.rest.dtos.track.TracksByDurationDto;
import java.util.List;

public class TracksByDurationResponse extends TrackResponse {
  public TracksByDurationResponse(Long min, Long max, List<Track> tracks) {
    this.setData(new TracksByDurationDto(min, max, tracks.size(), tracks));
  }
}
