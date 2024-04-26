package dev.nateschieber.groovesprings.rest.dtos.artist;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ArtistCreateDto(
    @NotNull @Size(min=1, max=255)
    String name
) implements ArtistDto { }
