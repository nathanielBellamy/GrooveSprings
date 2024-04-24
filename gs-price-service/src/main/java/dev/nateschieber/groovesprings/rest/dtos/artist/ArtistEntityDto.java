package dev.nateschieber.groovesprings.rest.dtos.artist;

import dev.nateschieber.groovesprings.rest.dtos.album.AlbumEntityDto;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackEntityDto;
import java.util.List;

public record ArtistEntityDto(long id, String name, List<AlbumEntityDto> albums, List<TrackEntityDto> tracks) { }
