package dev.nateschieber.groovesprings.matchers.track;

import dev.nateschieber.groovesprings.entities.Track;
import org.mockito.ArgumentMatcher;

public class TrackMatcher implements ArgumentMatcher<Track> {

  private Track left;

  public TrackMatcher(Track track) { this.left = track; }

  @Override
  public boolean matches(Track right) {
    return left.getId().equals(right.getId())
        && left.getTitle().equals(right.getTitle())
        && left.getDuration() == right.getDuration()
//        && left.getArtists().equals(right.getArtists())
//        && left.getAlbum().equals(right.getAlbum())
        && left.getReleaseDate().equals(right.getReleaseDate());
  }
}
