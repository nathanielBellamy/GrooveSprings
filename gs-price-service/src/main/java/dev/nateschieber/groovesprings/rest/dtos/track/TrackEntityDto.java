package dev.nateschieber.groovesprings.rest.dtos.track;

import dev.nateschieber.groovesprings.enums.Genre;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import java.time.LocalDate;
import java.util.List;

public record TrackEntityDto(
    Long id,
    Long duration,
    LocalDate releaseDate,
    AudioCodec audioCodec,
    List<Genre> genres) { }