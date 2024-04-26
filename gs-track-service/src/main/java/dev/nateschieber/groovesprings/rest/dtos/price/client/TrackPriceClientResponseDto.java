package dev.nateschieber.groovesprings.rest.dtos.price.client;

import dev.nateschieber.groovesprings.enums.ApiVersion;
import java.time.LocalDateTime;

public record TrackPriceClientResponseDto(
    LocalDateTime responseAt,
    ApiVersion apiVersion,
    TrackPriceClientDto data
) {
}
