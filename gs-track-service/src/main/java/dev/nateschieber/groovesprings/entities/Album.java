package dev.nateschieber.groovesprings.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dev.nateschieber.groovesprings.rest.dtos.album.AlbumEntityDto;
import enums.Genre;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "albums")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Album {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  private String name;
  private LocalDate releaseDate;

  @ElementCollection
  private List<Genre> genres;

  @OneToMany(mappedBy = "album")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @JsonBackReference
  Set<Track> tracks;

  @ManyToMany(mappedBy = "albums")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @JsonBackReference
  Set<Artist> artists;

  public Album() {};

  public Album(
      String name,
      List<Artist> artists,
      LocalDate releaseDate,
      List<Genre> genres) {
    this.name = name;
    this.setArtists(new HashSet<>(artists));
    this.genres = genres;
  }

  public Album(Long id, AlbumEntityDto dto) {
    this.id = id;
    Album album = dto.album();
    this.name = album.getName();
    this.artists = new HashSet<>(album.getArtists());
    this.genres = album.getGenres();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Track> getTracks() {
    List<Track> trackList = tracks.stream().collect(Collectors.toList());
    Collections.sort(trackList, Comparator.comparing(Track::getTrackNumber));
    return trackList;
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
}
