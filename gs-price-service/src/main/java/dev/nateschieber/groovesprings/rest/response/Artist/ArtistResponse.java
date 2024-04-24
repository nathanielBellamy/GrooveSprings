package dev.nateschieber.groovesprings.rest.response.Artist;

import dev.nateschieber.groovesprings.rest.dto.Artist.ArtistDto;
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
