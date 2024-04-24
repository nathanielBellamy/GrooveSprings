package dev.nateschieber.groovesprings.rest.dto.Album;

import dev.nateschieber.groovesprings.rest.dto.Artist.ArtistEntityDto;
import dev.nateschieber.groovesprings.rest.dto.Track.TrackEntityDto;
import java.time.LocalDate;
import java.util.List;

public record AlbumEntityDto(
    long id,
    String name,
    List<ArtistEntityDto> artists,
    LocalDate releaseDate,
    List<TrackEntityDto> tracks) { }
