package dev.nateschieber.groovesprings.rest.dtos.artist;

import dev.nateschieber.groovesprings.entities.Artist;
import java.util.List;

public record ArtistGetAllDto(List<Artist> artists) implements ArtistDto { }
