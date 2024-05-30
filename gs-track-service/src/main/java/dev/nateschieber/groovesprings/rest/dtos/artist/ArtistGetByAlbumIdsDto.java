package dev.nateschieber.groovesprings.rest.dtos.artist;

import java.util.List;

public record ArtistGetByAlbumIdsDto(
    List<Long> albumIds
) implements ArtistDto { }
