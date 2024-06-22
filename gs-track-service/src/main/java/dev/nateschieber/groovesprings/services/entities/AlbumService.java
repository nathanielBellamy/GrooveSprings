package dev.nateschieber.groovesprings.services.entities;

import dev.nateschieber.groovesprings.entities.Album;
import dev.nateschieber.groovesprings.entities.Artist;
import dev.nateschieber.groovesprings.repositories.AlbumRepository;
import dev.nateschieber.groovesprings.rest.dtos.album.AlbumCreateDto;
import dev.nateschieber.groovesprings.rest.dtos.album.AlbumEntityDto;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {
  private final AlbumRepository albumRepository;
  private final ArtistService artistService;

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

  public List<Album> findByTitle(String title) {
    return albumRepository.findByTitle(title);
  }

  public List<Album> findByArtistIds(List<Long> artistIds) {
    return this.albumRepository.findByArtistIds(artistIds);
  }

  public List<Album> findByPlaylistIds(List<Long> playlistIds) {
    return this.albumRepository.findByPlaylistIds(playlistIds);
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

  public Album findMatchOrCreate(String albumTitle, List<Artist> artists, LocalDate releaseDate) {
    // TODO:
    //    List<Album> albumMatches = albumRepository.findMatches(
    //            albumTitle,
    //            artists.stream().map(Artist::getId).toList(),
    //            releaseDate
    //    );
    List<Album> albumMatches = albumRepository.findByTitle(albumTitle);
    if (albumMatches.isEmpty()) {
      Album albumSaved = albumRepository.save(new Album(albumTitle, artists, null, Collections.emptyList()));
      artists.forEach(artist -> {
        artist.addAlbum(albumSaved);
        artistService.save(artist);
      });
      return albumSaved;
    } else {
      return albumMatches.getFirst();
    }
  }

  public void deleteAll() {
    albumRepository.deleteAll();
  }
}
