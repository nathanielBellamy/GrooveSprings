package dev.nateschieber.groovesprings.rest.dto.Album;

import dev.nateschieber.groovesprings.entities.Price;

public record AlbumPriceDto(Price price) implements AlbumDto { }
