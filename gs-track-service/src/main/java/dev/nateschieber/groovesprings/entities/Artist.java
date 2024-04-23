package dev.nateschieber.groovesprings.entities;

import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistEntityDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "artists")
public class Artist {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  private String name;

  @ManyToMany(mappedBy = "artists")
  private Set<Track> tracks;

  public Artist() {};

  public Artist(String name) {
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
}
