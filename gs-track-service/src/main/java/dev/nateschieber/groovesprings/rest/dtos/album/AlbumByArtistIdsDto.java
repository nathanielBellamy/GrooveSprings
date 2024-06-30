package dev.nateschieber.groovesprings.rest.dtos.album;

import dev.nateschieber.groovesprings.entities.Album;
import java.util.List;

public record AlbumByArtistIdsDto(
    List<Long> artistIds,
    int count,
    List<Album> albums
) implements AlbumDto {
}
