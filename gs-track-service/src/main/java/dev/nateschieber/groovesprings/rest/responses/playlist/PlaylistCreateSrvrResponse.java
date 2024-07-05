package dev.nateschieber.groovesprings.rest.responses.playlist;

import dev.nateschieber.groovesprings.entities.Playlist;
import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.rest.dtos.playlist.PlaylistCreateSrvrDto;

public class PlaylistCreateSrvrResponse extends PlaylistResponse {
    public PlaylistCreateSrvrResponse(Playlist playlist) {
        this.setData(
                new PlaylistCreateSrvrDto(
                        playlist.getId(),
                        playlist.getName(),
                        playlist.getTracks().stream().map(Track::toTrackSrvrDto).toList()
                )
        );
    }
}
