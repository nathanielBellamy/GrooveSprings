package dev.nateschieber.groovesprings.rest.dtos.album;

import java.util.List;

public record AlbumGetByArtistIdsDto(
    List<Long> artistIds
) implements AlbumDto{ }
