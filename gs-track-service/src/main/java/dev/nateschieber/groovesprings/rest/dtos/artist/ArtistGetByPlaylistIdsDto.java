package dev.nateschieber.groovesprings.rest.dtos.artist;

import java.util.List;

public record ArtistGetByPlaylistIdsDto(
    List<Long> playlistIds
) implements ArtistDto { }
