package dev.nateschieber.groovesprings.rest.dtos.playlist;

import dev.nateschieber.groovesprings.entities.Playlist;
import java.util.List;

public record PlaylistGetAllDto(int count, List<Playlist> playlists) implements PlaylistDto {
}
