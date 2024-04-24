package dev.nateschieber.groovesprings.rest.dtos.album;

import enums.Genre;
import java.time.LocalDate;
import java.util.List;

public record AlbumCreateDto(
    String name,
    List<Long> artistIds,
    LocalDate releaseDate,
    List<Genre> genres) implements AlbumDto { }
