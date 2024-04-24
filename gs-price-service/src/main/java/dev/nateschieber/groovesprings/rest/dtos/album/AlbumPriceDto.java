package dev.nateschieber.groovesprings.rest.dtos.album;

import dev.nateschieber.groovesprings.entities.Price;

public record AlbumPriceDto(Price price) implements AlbumDto { }
