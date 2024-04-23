package dev.nateschieber.groovesprings.rest.dtos.album;

import dev.nateschieber.groovesprings.entities.Album;
import dev.nateschieber.groovesprings.entities.Artist;
import java.util.List;

public record AlbumEntityDto(Album album) implements AlbumDto { }
