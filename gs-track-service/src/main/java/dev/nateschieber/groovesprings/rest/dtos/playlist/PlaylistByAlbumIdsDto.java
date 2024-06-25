package dev.nateschieber.groovesprings.rest.dtos.playlist;

import dev.nateschieber.groovesprings.entities.Playlist;
import java.util.List;

public record PlaylistByAlbumIdsDto(
    List<Long> albumIds,
    int count,
    List<Playlist> playlists
) implements PlaylistDto { }
