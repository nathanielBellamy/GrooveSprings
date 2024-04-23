package dev.nateschieber.groovesprings.composite;

import dev.nateschieber.groovesprings.entities.Album;
import dev.nateschieber.groovesprings.entities.Track;
import java.util.List;

public class AlbumWithTracks extends Album {
  private List<Track> tracks;

  public List<Track> getTracks() {
    return tracks;
  }

  public void setTracks(List<Track> tracks) {
    this.tracks = tracks;
  }
}
