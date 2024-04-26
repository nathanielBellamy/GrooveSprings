package dev.nateschieber.groovesprings.rest.dtos.price.client;

import dev.nateschieber.groovesprings.enums.ApiVersion;
import dev.nateschieber.groovesprings.rest.dtos.price.PriceDto;
import java.time.LocalDateTime;

public record PriceClientResponseDto(
    LocalDateTime responseAt,
    ApiVersion apiVersion,
    PriceClientDto data
) {
}
