package dev.nateschieber.groovesprings.rest.dtos.album;

import dev.nateschieber.groovesprings.enums.Genre;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public record AlbumCreateDto(
    @NotNull @Size(min=1, max=255)
    String name,
    List<Long> artistIds,
    LocalDate releaseDate,
    List<Genre> genres) implements AlbumDto { }
