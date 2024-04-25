package dev.nateschieber.groovesprings.rest.dtos.priced.track;

import dev.nateschieber.groovesprings.price.pricedEntities.PricedTrack;
import java.util.List;

public record PricedTracksByReleaseYearDto(int year, int count, List<PricedTrack> pricedTracks) implements PricedTrackDto {
}
