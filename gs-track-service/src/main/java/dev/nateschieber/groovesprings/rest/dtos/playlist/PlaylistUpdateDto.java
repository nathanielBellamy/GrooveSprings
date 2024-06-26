package dev.nateschieber.groovesprings.rest.dtos.playlist;

import java.util.List;

public record PlaylistUpdateDto(
        Long id,
        String name,
        List<Long> trackIds
) implements PlaylistDto { }
