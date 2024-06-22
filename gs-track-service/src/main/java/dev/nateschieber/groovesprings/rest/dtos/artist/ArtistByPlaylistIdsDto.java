package dev.nateschieber.groovesprings.rest.dtos.artist;

import dev.nateschieber.groovesprings.entities.Artist;

import java.util.List;

public record ArtistByPlaylistIdsDto(
    List<Long> playlistIds,
    int count,
    List<Artist> artists
) implements ArtistDto { }
