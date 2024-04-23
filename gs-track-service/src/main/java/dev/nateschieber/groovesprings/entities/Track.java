package dev.nateschieber.groovesprings.entities;

import dev.nateschieber.groovesprings.rest.dtos.track.TrackEntityDto;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "tracks")
public class Track {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  private String title;
  private long duration;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinTable(
      name = "album_to_track",
      joinColumns = @JoinColumn(name = "album_id"),
      inverseJoinColumns = @JoinColumn(name = "track_id")
  )
  private Album album;

  public Track() {}

  public Track(String title, long duration) {
    this.title = title;
    this.duration = duration;
  }

  public Track(Album album, String title, long duration) {
    this.album = album;
    this.title = title;
    this.duration = duration;
  }

  // useful for updating a track based on dto
  public Track(long id, TrackEntityDto dto) {
    this.id = id;
    Track track = dto.track();
    this.title = track.getTitle();
    this.duration = track.getDuration();
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public long getDuration() {
    return duration;
  }
}
