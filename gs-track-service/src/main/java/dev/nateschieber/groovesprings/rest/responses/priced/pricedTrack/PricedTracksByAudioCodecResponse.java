package dev.nateschieber.groovesprings.rest.responses.priced.pricedTrack;

import dev.nateschieber.groovesprings.price.pricedEntities.PricedTrack;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import dev.nateschieber.groovesprings.rest.dtos.priced.track.PricedTracksByAudioCodecDto;
import java.util.List;

public class PricedTracksByAudioCodecResponse extends PricedTrackResponse {
  public PricedTracksByAudioCodecResponse(AudioCodec audioCodec, List<PricedTrack> pricedTracks) {
    this.setData(new PricedTracksByAudioCodecDto(audioCodec, pricedTracks.size(),  pricedTracks));
  }
}
