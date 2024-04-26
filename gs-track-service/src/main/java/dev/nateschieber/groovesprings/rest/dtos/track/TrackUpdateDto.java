package dev.nateschieber.groovesprings.rest.dtos.track;

import dev.nateschieber.groovesprings.enums.AudioCodec;
import dev.nateschieber.groovesprings.enums.Genre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import org.antlr.v4.runtime.misc.NotNull;

public record TrackUpdateDto(
    @NotNull @Min(1l)
    Long id,
    List<Long> artistIds,
    Long albumId,
    @Size(min = 1, max = 255)
    String title,
    @NotNull @Min(1)
    int trackNumber,
    @NotNull @Min(1l)
    Long duration,
    @NotNull
    AudioCodec audioCodec,
    List<Genre> genres,
    LocalDate releaseDate) implements TrackDto { }
