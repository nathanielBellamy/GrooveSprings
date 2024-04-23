package dev.nateschieber.groovesprings.rest.responses.track;

import dev.nateschieber.groovesprings.rest.dtos.track.TrackDeletedDto;

public class TrackDeleteResponse extends TrackResponse {
  public TrackDeleteResponse(Long id) {
    this.setData(new TrackDeletedDto(id));
  }
}
