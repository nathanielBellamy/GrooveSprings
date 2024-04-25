package dev.nateschieber.groovesprings.rest.dtos.track;

import dev.nateschieber.groovesprings.enums.AudioCodec;
import java.time.LocalDate;
import java.util.List;

public record TrackCreateDto(
    List<Long> artistIds,
    long albumId,
    String title,
    int trackNumber,
    long duration,
    AudioCodec audioCodec,
    LocalDate releaseDate) implements TrackDto { }
