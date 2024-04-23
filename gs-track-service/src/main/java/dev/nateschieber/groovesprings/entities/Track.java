package dev.nateschieber.groovesprings.entities;

import dev.nateschieber.groovesprings.rest.dtos.track.TrackEntityDto;
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

  private String title;
  private long duration;

  public Track() {}

  public Track(String title, long duration) {
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
