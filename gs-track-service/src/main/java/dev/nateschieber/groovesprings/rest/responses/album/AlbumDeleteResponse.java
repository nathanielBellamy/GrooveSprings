package dev.nateschieber.groovesprings.rest.responses.album;

import dev.nateschieber.groovesprings.rest.dtos.album.AlbumDeletedDto;

public class AlbumDeleteResponse extends AlbumResponse {
  public AlbumDeleteResponse(Long id) {
    this.setData(new AlbumDeletedDto(id));
  }
}
