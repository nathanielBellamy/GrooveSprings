package dev.nateschieber.groovesprings.entities;

import com.fasterxml.jackson.annotation.*;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import dev.nateschieber.groovesprings.enums.Genre;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackUpdateDto;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tracks")
public class Track {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String title;
  private Integer duration;
  private int trackNumber;

  private String path;
  private Integer sampleRate;
  private Long bitRate;
  private Boolean isVariableBitRate;
  private Boolean isLossless;
  private int sf_frames;
  private int sf_samplerate;
  private int sf_channels;
  private int sf_format;
  private int sf_sections;
  private int sf_seekable;

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

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(
          name = "play_to_track",
          joinColumns = @JoinColumn(name="play_id"),
          inverseJoinColumns = @JoinColumn(name="track_id")
  )
  @JsonIgnore
  private Set<Play> plays;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "playlist_to_track",
      joinColumns = @JoinColumn(name="playlist_id"),
      inverseJoinColumns = @JoinColumn(name="track_id")
  )
  @JsonIgnore
  private Set<Playlist> playlists;

  public Track() {}

  public Track(
      Long id,
      List<Artist> artists,
      Album album,
      String title,
      int trackNumber,
      Integer duration,
      AudioCodec audioCodec,
      List<Genre> genres,
      LocalDate releaseDate,
      String path,
      Integer sampleRate,
      Long bitRate,
      Boolean isVariableBitRate,
      Boolean isLossless,
      int sf_frames,
      int sf_samplerate,
      int sf_channels,
      int sf_format,
      int sf_sections,
      int sf_seekable
  ) {
    this.id = id;
    this.album = album;
    this.artists = new HashSet<>(artists); // TODO: record order
    this.title = title;
    this.trackNumber = trackNumber;
    this.duration = duration;
    this.audioCodec = audioCodec;
    this.releaseDate = releaseDate;
    this.genres = genres;
    this.path = path;
    this.sampleRate = sampleRate;
    this.bitRate = bitRate;
    this.isVariableBitRate = isVariableBitRate;
    this.isLossless = isLossless;
    this.sf_frames = sf_frames;
    this.sf_samplerate = sf_samplerate;
    this.sf_channels = sf_channels;
    this.sf_format = sf_format;
    this.sf_sections = sf_sections;
    this.sf_seekable = sf_seekable;
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

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer newDuration) {
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

  public String getPath() {
    return path;
  }

  public Integer getSampleRate() {
    return sampleRate;
  }

  public Long getBitRate() {
    return bitRate;
  }

  public Boolean getVariableBitRate() {
    return isVariableBitRate;
  }

  public Boolean getLossless() {
    return isLossless;
  }

  public int getSf_frames() {
    return sf_frames;
  }

  public int getSf_samplerate() {
    return sf_samplerate;
  }

  public int getSf_channels() {
    return sf_channels;
  }

  public int getSf_format() {
    return sf_format;
  }

  public int getSf_sections() {
    return sf_sections;
  }

  public int getSf_seekable() {
    return sf_seekable;
  }

  public void addPlaylist(Playlist playlist) {
    playlists.add(playlist);
  }

  @Override
  public String toString() {
    return "Track{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", duration=" + duration +
            ", trackNumber=" + trackNumber +
            ", path='" + path + '\'' +
            ", sampleRate=" + sampleRate +
            ", bitRate=" + bitRate +
            ", isVariableBitRate=" + isVariableBitRate +
            ", isLossless=" + isLossless +
            ", genres=" + genres +
            ", audioCodec=" + audioCodec +
            ", releaseDate=" + releaseDate +
            ", album=" + album +
            ", artists=" + artists +
            '}';
  }
}
