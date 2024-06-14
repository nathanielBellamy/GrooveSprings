package dev.nateschieber.groovesprings.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dev.nateschieber.groovesprings.rest.dtos.album.AlbumEntityDto;
import dev.nateschieber.groovesprings.enums.Genre;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "albums")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "title")
public class Album {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String title;
  private LocalDate releaseDate;

  @ElementCollection
  @Enumerated(EnumType.STRING)
  private List<Genre> genres;

  @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @JsonBackReference
  Set<Track> tracks;

  @ManyToMany(mappedBy = "albums")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @JsonBackReference
  Set<Artist> artists;

  public Album() {};

  public Album(
      String title,
      List<Artist> artists,
      LocalDate releaseDate,
      List<Genre> genres) {
    this.title = title;
    this.setArtists(new HashSet<>(artists));
    this.genres = genres;
    this.releaseDate = releaseDate;
  }

  public Album(Long id, AlbumEntityDto dto) {
    this.id = id;
    Album album = dto.album();
    this.title = album.getTitle();
    this.artists = new HashSet<>(album.getArtists());
    this.genres = album.getGenres();
    this.releaseDate = album.getReleaseDate();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<Track> getTracks() {
    List<Track> trackList = tracks.stream().collect(Collectors.toList());
    Collections.sort(trackList, Comparator.comparing(Track::getTrackNumber));
    return trackList;
  }

  public void addTrack(Track track) {
    tracks.add(track);
  }

  public Set<Artist> getArtists() {
    return artists;
  }

  public void setArtists(Set<Artist> artists) {
    this.artists = artists;
  }

  public List<Genre> getGenres() {
    return genres;
  }

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  @Override
  public String toString() {
    return "Album{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", releaseDate=" + releaseDate +
            ", genres=" + genres +
            ", artists=" + artists +
            '}';
  }
}
