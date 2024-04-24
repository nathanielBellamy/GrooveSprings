package dev.nateschieber.groovesprings.rest.dtos.priced.track;

import dev.nateschieber.groovesprings.entities.priced.PricedTrack;
import java.util.List;

public record AllPricedTracksDto(List<PricedTrack> pricedTracks) implements PricedTrackDto {
}
