package dev.nateschieber.groovesprings.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dev.nateschieber.groovesprings.rest.dtos.album.AlbumEntityDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "albums")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Album {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  private String name;

  @OneToMany(mappedBy = "album")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  Set<Track> tracks;

  @ManyToMany(mappedBy = "albums")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  Set<Artist> artists;

  public Set<Track> getTracks() {
    return tracks;
  }

  public Album() {};

  public Album(String name) {
    this.name = name;
  }

  public Album(Long id, AlbumEntityDto dto) {
    this.id = id;
    this.name = dto.album().getName();
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

  public Set<Artist> getArtists() {
    return artists;
  }
}
