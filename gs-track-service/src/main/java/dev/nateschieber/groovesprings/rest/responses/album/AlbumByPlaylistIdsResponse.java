package dev.nateschieber.groovesprings.rest.responses.album;

import dev.nateschieber.groovesprings.entities.Album;
import dev.nateschieber.groovesprings.rest.dtos.album.AlbumByPlaylistIdsDto;

import java.util.List;

public class AlbumByPlaylistIdsResponse extends AlbumResponse {
    public AlbumByPlaylistIdsResponse(List<Album> albums, List<Long> playlistIds) {
        this.setData(new AlbumByPlaylistIdsDto(playlistIds, albums.size(), albums));
    };
}
