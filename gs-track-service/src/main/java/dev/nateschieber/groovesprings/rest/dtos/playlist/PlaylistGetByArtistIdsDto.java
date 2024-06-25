package dev.nateschieber.groovesprings.rest.dtos.playlist;

import java.util.List;

public record PlaylistGetByArtistIdsDto(
    List<Long> artistIds
) implements PlaylistDto { }
