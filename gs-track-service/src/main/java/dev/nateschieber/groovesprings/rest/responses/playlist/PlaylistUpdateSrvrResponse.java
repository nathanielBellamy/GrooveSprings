package dev.nateschieber.groovesprings.rest.responses.playlist;

import dev.nateschieber.groovesprings.entities.Playlist;
import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.rest.dtos.playlist.PlaylistUpdateSrvrDto;

public class PlaylistUpdateSrvrResponse extends PlaylistResponse {
    public PlaylistUpdateSrvrResponse(Playlist playlist) {
        this.setData(
                new PlaylistUpdateSrvrDto(
                        playlist.getId(),
                        playlist.getName(),
                        playlist.getTracks().stream().map(Track::toTrackSrvrDto).toList()
                )
        );
    }
}
