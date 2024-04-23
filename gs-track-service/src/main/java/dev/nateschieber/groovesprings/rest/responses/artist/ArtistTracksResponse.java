package dev.nateschieber.groovesprings.rest.responses.artist;

import dev.nateschieber.groovesprings.entities.Artist;
import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistTracksDto;
import java.util.stream.Collectors;

public class ArtistTracksResponse extends ArtistResponse{
  public ArtistTracksResponse(Artist artist) {
    this.setData(new ArtistTracksDto(artist.getName(), artist.getTracks().stream().collect(Collectors.toList()), artist.getTracks().size()));
  }
}
