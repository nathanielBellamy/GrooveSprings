package dev.nateschieber.groovesprings.rest.dtos.track;

import dev.nateschieber.groovesprings.entities.Price;
import java.util.List;

public record TrackPriceBulkDto(List<Price> prices) implements TrackDto {
}
