package dev.nateschieber.groovesprings.rest.dto.Artist;

import dev.nateschieber.groovesprings.entities.Price;

public record ArtistPriceDto(Price price) implements ArtistDto {
}
