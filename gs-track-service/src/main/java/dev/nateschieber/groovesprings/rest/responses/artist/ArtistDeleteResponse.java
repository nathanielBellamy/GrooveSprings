package dev.nateschieber.groovesprings.rest.responses.artist;

import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistDeletedDto;

public class ArtistDeleteResponse extends ArtistResponse {
  public ArtistDeleteResponse(Long id) {
    this.setData(new ArtistDeletedDto(id));
  }
}
