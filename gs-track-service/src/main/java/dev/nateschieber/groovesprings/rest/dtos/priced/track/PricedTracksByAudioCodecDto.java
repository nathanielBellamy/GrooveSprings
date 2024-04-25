package dev.nateschieber.groovesprings.rest.dtos.priced.track;

import dev.nateschieber.groovesprings.price.pricedEntities.PricedTrack;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import java.util.List;

public record PricedTracksByAudioCodecDto(AudioCodec audioCodec, int count, List<PricedTrack> pricedTracks) implements PricedTrackDto {
}
