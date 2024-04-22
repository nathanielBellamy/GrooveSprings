package dev.nateschieber.groovesprings.rest.dtos.track;

public record TrackCreateDto(long artistId, long albumId, String title, long duration) implements TrackDto {
}
