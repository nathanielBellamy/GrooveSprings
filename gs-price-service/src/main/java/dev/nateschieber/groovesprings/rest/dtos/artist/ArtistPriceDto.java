package dev.nateschieber.groovesprings.rest.dtos.artist;

import dev.nateschieber.groovesprings.entities.Price;

public record ArtistPriceDto(Price price) implements ArtistDto {
}
