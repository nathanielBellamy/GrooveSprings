package dev.nateschieber.groovesprings.rest.responses.artist;

import dev.nateschieber.groovesprings.entities.Artist;
import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistByPlaylistIdsDto;

import java.util.List;

public class ArtistGetByPlaylistIdsResponse extends ArtistResponse {
    public ArtistGetByPlaylistIdsResponse(List<Artist> artists, List<Long> playlistIds) {
        this.setData(new ArtistByPlaylistIdsDto(playlistIds, artists.size(), artists));
    }
}
