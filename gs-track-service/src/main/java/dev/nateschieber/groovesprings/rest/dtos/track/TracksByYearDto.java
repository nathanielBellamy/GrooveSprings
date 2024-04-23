package dev.nateschieber.groovesprings.rest.dtos.track;

import dev.nateschieber.groovesprings.entities.Track;
import java.util.List;

public record TracksByYearDto(Integer year, List<Track> tracks, Integer count) implements TrackDto { }
