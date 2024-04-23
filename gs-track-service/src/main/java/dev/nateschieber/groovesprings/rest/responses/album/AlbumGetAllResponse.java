package dev.nateschieber.groovesprings.rest.responses.album;

import dev.nateschieber.groovesprings.entities.Album;
import dev.nateschieber.groovesprings.rest.dtos.album.AlbumGetAllDto;
import java.util.List;

public class AlbumGetAllResponse extends AlbumResponse {
  public AlbumGetAllResponse(List<Album> albums) {
    this.setData(new AlbumGetAllDto(albums));
  }
}
