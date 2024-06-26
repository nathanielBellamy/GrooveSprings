package dev.nateschieber.groovesprings.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import dev.nateschieber.groovesprings.enums.Genre;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackDto;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackEntityDto;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackUpdateDto;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tracks")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "title")
public class Track {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String title;
  private Long duration;
  private int trackNumber;
  @ElementCollection
  @Enumerated(EnumType.STRING)
  private List<Genre> genres;

  @Enumerated(EnumType.STRING)
  private AudioCodec audioCodec;

  private LocalDate releaseDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinTable(
      name = "album_to_track",
      joinColumns = @JoinColumn(name = "album_id"),
      inverseJoinColumns = @JoinColumn(name = "track_id")
  )
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @JsonManagedReference
  private Album album;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "artist_to_track",
      joinColumns = @JoinColumn(name = "artist_id"),
      inverseJoinColumns = @JoinColumn(name = "track_id")
  )
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @JsonManagedReference
  private Set<Artist> artists;

  public Track() {}

  public Track(
      Long id,
      List<Artist> artists,
      Album album,
      String title,
      int trackNumber,
      Long duration,
      AudioCodec audioCodec,
      List<Genre> genres,
      LocalDate releaseDate) {
    this.id = id;
    this.album = album;
    this.artists = new HashSet<>(artists); // TODO: record order
    this.title = title;
    this.trackNumber = trackNumber;
    this.duration = duration;
    this.audioCodec = audioCodec;
    this.releaseDate = releaseDate;
    this.genres = genres;
  }

  public Track(TrackUpdateDto dto, List<Artist> newArtists, Album newAlbum) {
    this.id = dto.id();
    this.title = dto.title();
    this.trackNumber = dto.trackNumber();
    this.duration = dto.duration();
    this.audioCodec = dto.audioCodec();
    this.releaseDate = dto.releaseDate();
    this.genres = dto.genres();
    this.artists = newArtists.stream().collect(Collectors.toSet());
    this.album = newAlbum;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String newTitle) {
    title = newTitle;
  }

  public int getTrackNumber() {
    return trackNumber;
  }

  public long getDuration() {
    return duration;
  }

  public void setDuration(Long newDuration) {
    duration = newDuration;
  }

  public Album getAlbum() {
    return album;
  }

  public List<Artist> getArtists() {
    if (artists == null) {
      return Collections.emptyList();
    } else {
      return artists.stream().toList();
    }
  }

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  public AudioCodec getAudioCodec() {
    return audioCodec;
  }

  public List<Genre> getGenres() {
    return genres;
  }
}
