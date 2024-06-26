package dev.nateschieber.groovesprings.rest.responses.playlist;

import dev.nateschieber.groovesprings.entities.Playlist;
import dev.nateschieber.groovesprings.rest.dtos.playlist.PlaylistEntityDto;

public class PlaylistUpdateResponse extends PlaylistResponse {
    public PlaylistUpdateResponse(Playlist playlist) {
        this.setData(new PlaylistEntityDto(playlist));
    }
}
