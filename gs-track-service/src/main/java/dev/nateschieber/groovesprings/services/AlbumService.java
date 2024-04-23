package dev.nateschieber.groovesprings.services;

import dev.nateschieber.groovesprings.entities.Album;
import dev.nateschieber.groovesprings.repositories.AlbumRepository;
import dev.nateschieber.groovesprings.rest.dtos.album.AlbumEntityDto;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {
  private AlbumRepository albumRepository;

  @Autowired
  public AlbumService(
      AlbumRepository albumRepository) {
    this.albumRepository = albumRepository;
  }

  public List<Album> findAll() {
    return this.albumRepository.findAll();
  }

  public Optional<Album> findById(Long id) {
    return this.albumRepository.findById(id);
  }

  public void deleteById(Long id) {
    this.albumRepository.deleteById(id);
  }

  public Album update(Long id, AlbumEntityDto dto) {
    Album updatedAlbum = new Album(id, dto);
    return this.albumRepository.save(updatedAlbum);
  }

  public Album save(Album album) {
    Album savedAlbum = this.albumRepository.save(album);

    return savedAlbum;
  }
}
