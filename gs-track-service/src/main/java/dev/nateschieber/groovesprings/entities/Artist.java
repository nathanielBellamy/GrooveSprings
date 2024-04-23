package dev.nateschieber.groovesprings.entities;

import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistEntityDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "artists")
public class Artist {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  private String name;

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
