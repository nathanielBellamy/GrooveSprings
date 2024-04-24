package dev.nateschieber.groovesprings.rest.dto.Artist;

import dev.nateschieber.groovesprings.rest.dto.Album.AlbumEntityDto;
import dev.nateschieber.groovesprings.rest.dto.Track.TrackEntityDto;
import java.util.List;

public record ArtistEntityDto(long id, String name, List<AlbumEntityDto> albums, List<TrackEntityDto> tracks) { }
