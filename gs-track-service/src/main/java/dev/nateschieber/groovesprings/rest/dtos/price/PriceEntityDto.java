package dev.nateschieber.groovesprings.rest.dtos.price;

import dev.nateschieber.groovesprings.enums.EntityType;
import java.time.LocalDateTime;

public record PriceEntityDto(
    long id,
    LocalDateTime at,
    EntityType entityType,
    long entityId,
    long usdCents
) implements PriceDto {
}
