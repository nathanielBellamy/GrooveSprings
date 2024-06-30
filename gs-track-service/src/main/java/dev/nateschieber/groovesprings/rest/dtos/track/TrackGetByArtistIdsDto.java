package dev.nateschieber.groovesprings.rest.dtos.track;

import java.util.List;

public record TrackGetByArtistIdsDto(
    List<Long> artistIds
) implements TrackDto {
}
