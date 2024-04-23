package dev.nateschieber.groovesprings.rest.responses.artist;

import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistDto;
import dev.nateschieber.groovesprings.rest.responses.GsHttpResponse;

public abstract class ArtistResponse extends GsHttpResponse {
  private ArtistDto data;

  public ArtistDto getData() {
    return data;
  }

  public void setData(ArtistDto data) {
    this.data = data;
  }
}
