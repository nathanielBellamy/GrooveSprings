package dev.nateschieber.groovesprings.services.entities;

import dev.nateschieber.groovesprings.entities.Artist;
import dev.nateschieber.groovesprings.repositories.ArtistRepository;
import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistBulkCreateDto;
import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistEntityDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {
  private ArtistRepository artistRepository;

  @Autowired
  public ArtistService(
      ArtistRepository artistRepository) {
    this.artistRepository = artistRepository;
  }

  public List<Artist> findAll() {
    return this.artistRepository.findAll();
  }

  public List<Artist> findByAlbumIds(List<Long> albumIds) {
    return this.artistRepository.findByAlbumIds(albumIds);
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

  public void saveAll(List<Artist> artists) {
    artistRepository.saveAll(artists);
  }

  public List<Artist> findAllById(List<Long> ids) {
    return artistRepository.findAllById(ids);
  }

  public List<Artist> createAllFromDto(ArtistBulkCreateDto dto) {
    return artistRepository.saveAll(dto.artists());
  }

  public List<Artist> findOrCreateAllByName(List<String> artistNames) {
    List<Artist> artistsFound = artistRepository.findAllByNames(artistNames);
    List<String> artistNamesFound = artistsFound
            .stream()
            .map(Artist::getName)
            .toList();

    List<String> artistNamesToCreate = artistNames
            .stream()
            .filter(an -> !artistNamesFound.contains(an))
            .toList();

    List<Artist> artistsCreated = artistNamesToCreate
            .stream()
            .map(an -> artistRepository.save(new Artist(an)))
            .toList();

    return Stream.concat(artistsFound.stream(), artistsCreated.stream()).toList();
  }

  public void deleteAll() {
    artistRepository.deleteAll();
  }
}
