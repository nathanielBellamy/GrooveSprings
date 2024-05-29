package dev.nateschieber.groovesprings.services.entities;

import dev.nateschieber.groovesprings.entities.Album;
import dev.nateschieber.groovesprings.entities.Artist;
import dev.nateschieber.groovesprings.repositories.AlbumRepository;
import dev.nateschieber.groovesprings.rest.dtos.album.AlbumCreateDto;
import dev.nateschieber.groovesprings.rest.dtos.album.AlbumEntityDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {
  private AlbumRepository albumRepository;
  private ArtistService artistService;

  @Autowired
  public AlbumService(
      AlbumRepository albumRepository,
      ArtistService artistService) {
    this.albumRepository = albumRepository;
    this.artistService = artistService;
  }

  public List<Album> findAll() {
    return this.albumRepository.findAll();
  }

  public Optional<Album> findById(Long id) {
    return this.albumRepository.findById(id);
  }

  public List<Album> findByArtistIds(List<Long> artistIds) {
    return this.albumRepository.findByArtistIds(artistIds);
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

  public Album createFromDto(AlbumCreateDto dto) {
    List<Artist> artists = artistService.findAllById(dto.artistIds());
    Album album = new Album(dto.name(), artists, dto.releaseDate(), dto.genres());
    Album savedAlbum = albumRepository.save(album);
    // NOTE:
    //   - JPA will update ManyToMany joins only when Artist is updated
    //   - so update the artists here
    List<Artist> updatedArtists = artists.stream().map(a -> {
        a.addAlbum(savedAlbum);
        return a;
    }).collect(Collectors.toList());
    artistService.saveAll(updatedArtists);
    return savedAlbum;
  }
}
