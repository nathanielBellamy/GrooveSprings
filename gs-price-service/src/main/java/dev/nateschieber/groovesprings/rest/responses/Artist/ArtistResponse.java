package dev.nateschieber.groovesprings.rest.responses.Artist;

import dev.nateschieber.groovesprings.rest.dtos.Artist.ArtistDto;
import dev.nateschieber.groovesprings.rest.responses.GsHttpResponse;

public class ArtistResponse extends GsHttpResponse {

  private ArtistDto data;

  public ArtistDto getData() {
    return data;
  }

  public void setData(ArtistDto data) {
    this.data = data;
  }
}
