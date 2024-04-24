package dev.nateschieber.groovesprings.rest.response.Album;

import dev.nateschieber.groovesprings.rest.dto.Album.AlbumDto;
import dev.nateschieber.groovesprings.rest.responses.GsHttpResponse;

public abstract class AlbumResponse extends GsHttpResponse {

  private AlbumDto data;

  public AlbumDto getData() {
    return data;
  }

  public void setData(AlbumDto data) {
    this.data = data;
  }
}
