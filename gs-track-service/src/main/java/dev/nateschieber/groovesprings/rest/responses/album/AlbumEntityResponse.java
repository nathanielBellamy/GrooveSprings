package dev.nateschieber.groovesprings.rest.responses.album;

import dev.nateschieber.groovesprings.entities.Album;
import dev.nateschieber.groovesprings.rest.dtos.album.AlbumEntityDto;

public class AlbumEntityResponse extends AlbumResponse {
  public AlbumEntityResponse(Album album) {
    this.setData(new AlbumEntityDto(album));
  }
}
