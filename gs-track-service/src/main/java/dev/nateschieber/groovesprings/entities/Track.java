package dev.nateschieber.groovesprings.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tracks")
public class Track {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  private long artistId;
  private long albumId;

  private String title;
  private long duration;

  public Track() {}

  public Track(long artistId, long albumId, String title, long duration) {
    this.artistId = artistId;
    this.albumId = albumId;
    this.title = title;
    this.duration = duration;
  }

  public long getId() {
    return id;
  }

  public long getArtistId() {
    return artistId;
  }

  public long getAlbumId() {
    return albumId;
  }

  public String getTitle() {
    return title;
  }

  public long getDuration() {
    return duration;
  }
}
