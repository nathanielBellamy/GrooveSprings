package dev.nateschieber.groovesprings.rest.dtos.playlist;

import dev.nateschieber.groovesprings.entities.Playlist;

public record PlaylistEntityDto(Playlist playlist) implements PlaylistDto {
}
