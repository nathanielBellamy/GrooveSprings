package dev.nateschieber.groovesprings.rest.dtos.track;

import dev.nateschieber.groovesprings.enums.Genre;
import dev.nateschieber.groovesprings.enums.MediaType;
import java.time.LocalDate;
import java.util.List;

public record TrackEntityDto(
    long id,
    Long duration,
    LocalDate releaseDate,
    MediaType mediaType,
    List<Genre> genres) { }