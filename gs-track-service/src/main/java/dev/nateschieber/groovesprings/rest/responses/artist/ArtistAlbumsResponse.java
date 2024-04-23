package dev.nateschieber.groovesprings.rest.responses.artist;

import dev.nateschieber.groovesprings.entities.Artist;
import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistAlbumsDto;

public class ArtistAlbumsResponse extends ArtistResponse {

  public ArtistAlbumsResponse(Artist artist) {
    this.setData(new ArtistAlbumsDto(artist.getName(), artist.getAlbums()));
  }
}
