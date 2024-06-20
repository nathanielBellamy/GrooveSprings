package dev.nateschieber.groovesprings.rest.responses.playlist;

import dev.nateschieber.groovesprings.rest.dtos.playlist.PlaylistDto;
import dev.nateschieber.groovesprings.rest.responses.GsHttpResponse;

public class PlaylistResponse extends GsHttpResponse {
  private PlaylistDto data;

  public PlaylistDto getData() {
    return data;
  }

  public void setData(PlaylistDto data) {
    this.data = data;
  }
}
