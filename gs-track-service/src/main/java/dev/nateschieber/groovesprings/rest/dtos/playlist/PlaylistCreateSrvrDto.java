package dev.nateschieber.groovesprings.rest.dtos.playlist;

import dev.nateschieber.groovesprings.rest.dtos.track.TrackSrvrDto;

import java.util.List;

public record PlaylistCreateSrvrDto(
        Long id,
        String name,
        List<TrackSrvrDto> tracks
) implements PlaylistDto {
}
