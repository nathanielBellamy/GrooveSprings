package dev.nateschieber.groovesprings.rest.dtos.priced.track;

import dev.nateschieber.groovesprings.entities.priced.PricedTrack;
import java.util.List;

public record PricedTracksByReleaseYearDto(int year, List<PricedTrack> pricedTracks) implements PricedTrackDto {
}
