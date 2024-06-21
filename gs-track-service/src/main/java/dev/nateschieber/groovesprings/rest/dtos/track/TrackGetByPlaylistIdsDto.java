package dev.nateschieber.groovesprings.rest.dtos.track;

import java.util.List;

public record TrackGetByPlaylistIdsDto(
        List<Long> playlistIds
) implements TrackDto {
}
