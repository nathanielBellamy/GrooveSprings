package dev.nateschieber.groovesprings.rest.dtos.album;

import java.util.List;

public record AlbumGetByPlaylistIdsDto(
    List<Long> playlistIds
)
implements AlbumDto { }
