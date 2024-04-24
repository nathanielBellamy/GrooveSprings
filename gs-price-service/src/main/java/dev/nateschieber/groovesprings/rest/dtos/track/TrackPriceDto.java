package dev.nateschieber.groovesprings.rest.dtos.track;

import dev.nateschieber.groovesprings.entities.Price;

public record TrackPriceDto(Price price) implements TrackDto { }
