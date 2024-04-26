package dev.nateschieber.groovesprings.rest.responses.track;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import dev.nateschieber.groovesprings.rest.dtos.track.TracksByAudioCodecDto;
import java.util.List;

public class TracksByAudioCodecResponse extends TrackResponse {
  public TracksByAudioCodecResponse(AudioCodec audioCodec, List<Track> tracks) {
    this.setData( new TracksByAudioCodecDto(audioCodec, tracks.size(), tracks));
  }
}
