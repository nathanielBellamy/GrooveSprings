package dev.nateschieber.groovesprings.rest.dtos.playlist;

import dev.nateschieber.groovesprings.entities.Playlist;
import dev.nateschieber.groovesprings.entities.Track;
import java.util.List;

public record PlaylistTracksDto(Playlist playlist, int trackCount, List<Track> tracks) implements PlaylistDto {
}
