package dev.nateschieber.groovesprings.rest.dto.Track;

import enums.Genre;
import java.time.LocalDate;
import java.util.List;

public record TrackEntityDto(long id, Long duration, LocalDate releaseDate, List<Genre> genres) { }