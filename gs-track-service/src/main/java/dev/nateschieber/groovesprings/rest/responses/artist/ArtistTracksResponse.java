package dev.nateschieber.groovesprings.rest.responses.artist;

import dev.nateschieber.groovesprings.entities.Artist;
import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistTracksDto;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ArtistTracksResponse extends ArtistResponse{
  public ArtistTracksResponse(Artist artist) {
    Set<Track> tracks = artist.getTracks();
    this.setData(new ArtistTracksDto(artist.getName(), tracks.size(), tracks.stream().collect(Collectors.toList())));
  }
}
