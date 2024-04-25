package dev.nateschieber.groovesprings.rest.dtos.priced.track;

import dev.nateschieber.groovesprings.entities.priced.PricedTrack;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import java.util.List;

public record PricedTracksByAudioCodecDto(AudioCodec audioCodec, List<PricedTrack> pricedTracks, int count) implements PricedTrackDto {
}
