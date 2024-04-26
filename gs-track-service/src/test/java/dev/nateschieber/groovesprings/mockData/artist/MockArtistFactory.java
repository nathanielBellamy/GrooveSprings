package dev.nateschieber.groovesprings.mockData.artist;

import dev.nateschieber.groovesprings.entities.Artist;
import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistEntityDto;
import java.util.List;

public class MockArtistFactory {

  public static List<Artist> defaultArtists() {
    return List.of(
        new Artist(1l, "Beach House"),
        new Artist(2l, "Sam Cooke"),
        new Artist(3l, "Girls")
    );
  }
}
