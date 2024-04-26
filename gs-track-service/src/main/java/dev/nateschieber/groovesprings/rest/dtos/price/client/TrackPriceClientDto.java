package dev.nateschieber.groovesprings.rest.dtos.price.client;

import dev.nateschieber.groovesprings.price.Price;

public record TrackPriceClientDto(Price price) implements PriceClientDto {
}
