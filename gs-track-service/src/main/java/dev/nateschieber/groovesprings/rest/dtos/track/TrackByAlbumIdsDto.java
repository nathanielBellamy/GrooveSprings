package dev.nateschieber.groovesprings.rest.dtos.track;

import dev.nateschieber.groovesprings.entities.Track;
import java.util.List;

public record TrackByAlbumIdsDto(
    List<Track> tracks,
    int count,
    List<Long> albumIds
) implements TrackDto { }
