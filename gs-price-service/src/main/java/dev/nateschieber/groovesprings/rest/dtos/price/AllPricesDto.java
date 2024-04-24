package dev.nateschieber.groovesprings.rest.dtos.price;

import dev.nateschieber.groovesprings.entities.Price;
import java.util.List;

public record AllPricesDto(List<Price> prices) implements PriceDto {
}
