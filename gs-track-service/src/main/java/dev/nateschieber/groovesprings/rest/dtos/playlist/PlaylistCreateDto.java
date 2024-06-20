package dev.nateschieber.groovesprings.rest.dtos.playlist;

import java.util.List;

public record PlaylistCreateDto(
    String name,
    List<Long> trackIds
) { }
