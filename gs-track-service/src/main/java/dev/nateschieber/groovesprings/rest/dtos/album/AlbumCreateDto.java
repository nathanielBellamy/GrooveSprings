package dev.nateschieber.groovesprings.rest.dtos.album;

import java.util.List;

public record AlbumCreateDto(String name, List<Long> artistIds) implements AlbumDto { }
