package dev.nateschieber.groovesprings.rest.dtos.Artist;

import dev.nateschieber.groovesprings.rest.dtos.Album.AlbumEntityDto;
import dev.nateschieber.groovesprings.rest.dtos.Track.TrackEntityDto;
import java.util.List;

public record ArtistEntityDto(long id, String name, List<AlbumEntityDto> albums, List<TrackEntityDto> tracks) { }
