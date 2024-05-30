package dev.nateschieber.groovesprings.rest.dtos.track;

import java.util.List;

public record TrackGetByAlbumIdsDto(
    List<Long> albumIds
) implements TrackDto  { }
