package dev.nateschieber.groovesprings.rest.dtos.album;

import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistEntityDto;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackEntityDto;
import java.time.LocalDate;
import java.util.List;

public record AlbumEntityDto(
    long id,
    String name,
    List<ArtistEntityDto> artists,
    LocalDate releaseDate,
    List<TrackEntityDto> tracks) { }
