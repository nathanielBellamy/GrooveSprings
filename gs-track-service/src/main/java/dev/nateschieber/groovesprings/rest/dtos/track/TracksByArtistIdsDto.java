package dev.nateschieber.groovesprings.rest.dtos.track;

import dev.nateschieber.groovesprings.entities.Track;
import java.util.List;

public record TracksByArtistIdsDto(
    List<Long> artistIds,
    int count,
    List<Track> tracks
) implements TrackDto {
}
