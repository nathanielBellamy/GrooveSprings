package dev.nateschieber.groovesprings.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.List;
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
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Album album;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "artist_to_track",
      joinColumns = @JoinColumn(name = "artist_id"),
      inverseJoinColumns = @JoinColumn(name = "track_id")
  )
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Set<Artist> artists;

  public Track() {}

  public Track(List<Artist> artists, Optional<Album> album, String title, long duration) {
    this.album = album.orElse(null);
    this.artists = new HashSet<>(artists); // TODO: record order
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

  public Album getAlbum() {
    return album;
  }

  public Set<Artist> getArtists() {
    return artists;
  }
}
