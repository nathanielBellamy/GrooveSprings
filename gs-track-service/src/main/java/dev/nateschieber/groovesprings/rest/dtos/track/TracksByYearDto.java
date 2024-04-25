package dev.nateschieber.groovesprings.rest.dtos.track;

import dev.nateschieber.groovesprings.entities.Track;
import java.util.List;

public record TracksByYearDto(Integer year, Integer count, List<Track> tracks) implements TrackDto { }
