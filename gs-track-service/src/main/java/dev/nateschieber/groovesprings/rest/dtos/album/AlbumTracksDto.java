package dev.nateschieber.groovesprings.rest.dtos.album;

import dev.nateschieber.groovesprings.entities.Album;
import dev.nateschieber.groovesprings.entities.Track;
import java.util.List;

public record AlbumTracksDto(Album album, List<Track> tracks) implements AlbumDto { }
