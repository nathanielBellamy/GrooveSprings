package dev.nateschieber.groovesprings.rest.dtos.track;

import java.util.List;

public record TrackEntityBulkDto(List<TrackEntityDto> tracks) implements TrackDto { }
