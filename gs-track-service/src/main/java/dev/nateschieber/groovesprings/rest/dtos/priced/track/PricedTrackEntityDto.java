package dev.nateschieber.groovesprings.rest.dtos.priced.track;

import dev.nateschieber.groovesprings.price.pricedEntities.PricedTrack;

public record PricedTrackEntityDto(PricedTrack pricedTrack) implements PricedTrackDto {
}
