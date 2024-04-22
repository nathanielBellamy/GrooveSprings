package dev.nateschieber.groovesprings.rest.responses.track;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackEntityDto;

public class TrackEntityResponse extends TrackResponse {
  public TrackEntityResponse(Track track) {
    this.setData(new TrackEntityDto(track));
  }
}
