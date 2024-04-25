package dev.nateschieber.groovesprings.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import dev.nateschieber.groovesprings.enums.Genre;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackEntityDto;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "tracks")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "title")
public class Track {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String title;
  private long duration;
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
      Optional<Album> album,
      String title,
      int trackNumber,
      long duration,
      AudioCodec audioCodec,
      List<Genre> genres,
      LocalDate releaseDate) {
    this.id = id;
    this.album = album.orElse(null);
    this.artists = new HashSet<>(artists); // TODO: record order
    this.title = title;
    this.trackNumber = trackNumber;
    this.duration = duration;
    this.audioCodec = audioCodec;
    this.releaseDate = releaseDate;
    this.genres = genres;
  }

  // useful for updating a track based on dto
  public Track(long id, TrackEntityDto dto) {
    this.id = id;
    Track track = dto.track();
    this.title = track.getTitle();
    this.trackNumber = track.getTrackNumber();
    this.duration = track.getDuration();
    this.audioCodec = track.getAudioCodec();
    this.releaseDate = track.getReleaseDate();
    this.genres = track.getGenres();
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public int getTrackNumber() {
    return trackNumber;
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
