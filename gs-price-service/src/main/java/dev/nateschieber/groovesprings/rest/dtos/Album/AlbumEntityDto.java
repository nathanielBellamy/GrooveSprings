package dev.nateschieber.groovesprings.rest.dtos.Album;

import dev.nateschieber.groovesprings.rest.dtos.Artist.ArtistEntityDto;
import dev.nateschieber.groovesprings.rest.dtos.Track.TrackEntityDto;
import java.time.LocalDate;
import java.util.List;

public record AlbumEntityDto(
    long id,
    String name,
    List<ArtistEntityDto> artists,
    LocalDate releaseDate,
    List<TrackEntityDto> tracks) { }
