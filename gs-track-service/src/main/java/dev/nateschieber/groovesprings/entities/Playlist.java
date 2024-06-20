package dev.nateschieber.groovesprings.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="playlists")
public class Playlist {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String name;

  @ElementCollection
  private List<Long> trackIds;

  @ManyToMany(mappedBy="playlists")
  @JsonBackReference
  @JsonIgnore
  private Set<Track> tracks;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "artist_to_playlist",
      joinColumns = @JoinColumn(name="artist_id"),
      inverseJoinColumns = @JoinColumn(name="playlist_id")
  )
  @JsonManagedReference
  @JsonIgnore
  private Set<Artist> artists;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "album_to_playlist",
      joinColumns = @JoinColumn(name="album_id"),
      inverseJoinColumns = @JoinColumn(name="playlist_id")
  )
  @JsonManagedReference
  @JsonIgnore
  private Set<Album> albums;

  public Playlist() {}

  public Playlist(String name, List<Long> trackIds) {
    this.name = name;
    this.trackIds = trackIds;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Long> getTrackIds() {
    return trackIds;
  }

  public void setTrackIds(List<Long> trackIds) {
    this.trackIds = trackIds;
  }

  public List<Track> getTracks() {
    if (tracks == null) { return Collections.emptyList(); }

    return tracks
        .stream()
        .sorted((tA, tB) -> trackIds.indexOf(tA.getId()) <  trackIds.indexOf(tB.getId()) ? -1 : 1)
        .toList();

  }

  public void setTracks(Set<Track> tracks) {
    this.tracks = tracks;
  }

  public Set<Artist> getArtists() {
    return artists;
  }

  public void setArtists(Set<Artist> artists) {
    this.artists = artists;
  }

  public Set<Album> getAlbums() {
    return albums;
  }

  public void setAlbums(Set<Album> albums) {
    this.albums = albums;
  }
}
