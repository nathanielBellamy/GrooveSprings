package dev.nateschieber.groovesprings.rest.dtos.artist;

import dev.nateschieber.groovesprings.entities.Track;
import java.util.List;

public record ArtistTracksDto(String artist, List<Track> tracks, Integer count) implements ArtistDto { }
