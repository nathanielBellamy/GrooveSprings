package dev.nateschieber.groovesprings.rest.responses.track;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackGetAllDto;
import java.util.List;

public class TrackGetAllResponse extends TrackResponse {
  public TrackGetAllResponse(List<Track> tracks) {
    this.setData(new TrackGetAllDto(tracks.size(), tracks));
  }
}
