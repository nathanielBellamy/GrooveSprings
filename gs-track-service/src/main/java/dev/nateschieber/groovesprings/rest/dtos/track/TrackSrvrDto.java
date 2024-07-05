package dev.nateschieber.groovesprings.rest.dtos.track;

public record TrackSrvrDto(
        Long id,
        String path,
        String json
) implements TrackDto {}
