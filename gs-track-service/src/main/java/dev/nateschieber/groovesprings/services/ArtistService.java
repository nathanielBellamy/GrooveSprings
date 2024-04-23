package dev.nateschieber.groovesprings.services;

import dev.nateschieber.groovesprings.entities.Artist;
import dev.nateschieber.groovesprings.repositories.ArtistRepository;
import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistEntityDto;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {
  private ArtistRepository artistRepository;
  private TrackService trackService;

  @Autowired
  public ArtistService(
      ArtistRepository artistRepository,
      TrackService trackService) {
    this.artistRepository = artistRepository;
    this.trackService = trackService;
  }

  public List<Artist> findAll() {
    return this.artistRepository.findAll();
  }

  public Optional<Artist> findById(Long id) {
    return this.artistRepository.findById(id);
  }

  public void deleteById(Long id) {
    this.artistRepository.deleteById(id);
  }

  public Artist update(Long id, ArtistEntityDto dto) {
    Artist updatedArtist = new Artist(id, dto);
    return this.artistRepository.save(updatedArtist);
  }

  public Artist save(Artist artist) {
    Artist savedArtist = this.artistRepository.save(artist);

    return savedArtist;
  }
}
