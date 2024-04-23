package dev.nateschieber.groovesprings.rest.dtos.track;

import java.util.List;

public record TrackCreateDto(List<Long> artistIds, long albumId, String title, long duration) implements TrackDto { }
