package dev.nateschieber.groovesprings.rest.responses.track;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import dev.nateschieber.groovesprings.rest.dtos.track.TracksByMediaTypeDto;
import java.util.List;

public class TracksByMediaTypeResponse extends TrackResponse {
  public TracksByMediaTypeResponse(AudioCodec audioCodec, List<Track> tracks) {
    this.setData( new TracksByMediaTypeDto(audioCodec, tracks, tracks.size()));
  }
}
