package dev.nateschieber.groovesprings.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistEntityDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "artists")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Artist {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "artists")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @JsonBackReference
  private Set<Track> tracks;

  @ManyToMany(cascade = CascadeType.MERGE )
  @JoinTable(
      name = "album_to_artist",
      joinColumns = @JoinColumn(name = "album_id"),
      inverseJoinColumns = @JoinColumn(name = "artist_id")
  )
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @JsonManagedReference
  @JsonIgnore
  private Set<Album> albums;

  public Artist() {};

  public Artist(String name) {
    this.name = name;
  }

  public Artist(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Artist(Long id, ArtistEntityDto dto) {
    this.id = id;
    this.name = dto.artist().getName();
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Set<Track> getTracks() {
    return tracks;
  }

  public void addTrack(Track track) {
    tracks.add(track);
  }

  public Set<Album> getAlbums() {
    return albums;
  }

  public void setAlbums(Set<Album> albums) {
    this.albums = albums;
  }

  public void addAlbum(Album album) {
    albums.add(album);
  }
}
