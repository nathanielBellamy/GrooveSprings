package dev.nateschieber.groovesprings.rest.responses.track;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.rest.dtos.track.TracksByPlaylistIdsDto;

import java.util.List;

public class TracksByPlaylistIdsResponse extends TrackResponse {
    public TracksByPlaylistIdsResponse(List<Track> tracks, List<Long> playlistIds) {
        this.setData( new TracksByPlaylistIdsDto(tracks, tracks.size(), playlistIds));
    }
}
