package dev.nateschieber.groovesprings.rest.dtos.artist;

import dev.nateschieber.groovesprings.entities.Album;
import java.util.Set;

public record ArtistAlbumsDto(String artistName, Set<Album> albums) implements ArtistDto { }
