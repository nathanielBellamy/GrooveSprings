package dev.nateschieber.groovesprings.rest.dtos.track;

import dev.nateschieber.groovesprings.enums.AudioCodec;
import dev.nateschieber.groovesprings.enums.Genre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


public record TrackCreateDto(
    List<Long> artistIds,
    Long albumId,
    @Size(min = 1, max = 255)
    String title,
    @NotNull @Min(1)
    int trackNumber,
    @NotNull @Min(1)
    Integer duration,
    @NotNull
    AudioCodec audioCodec,
    List<Genre> genres,
    LocalDate releaseDate) implements TrackDto { }
