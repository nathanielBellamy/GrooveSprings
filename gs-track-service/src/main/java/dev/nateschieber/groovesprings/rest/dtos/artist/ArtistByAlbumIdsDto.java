package dev.nateschieber.groovesprings.rest.dtos.artist;

import dev.nateschieber.groovesprings.entities.Artist;
import java.util.List;

public record ArtistByAlbumIdsDto(
    List<Artist> artists,
    int count,
    List<Long> albumIds
) implements ArtistDto { }
