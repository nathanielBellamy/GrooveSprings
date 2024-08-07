package dev.nateschieber.groovesprings.controllers;

import dev.nateschieber.groovesprings.entities.Artist;
import dev.nateschieber.groovesprings.helpers.HttpHelper;
import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistBulkCreateDto;
import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistCreateDto;
import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistEntityDto;
import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistGetByAlbumIdsDto;
import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistGetByPlaylistIdsDto;
import dev.nateschieber.groovesprings.rest.responses.artist.*;
import dev.nateschieber.groovesprings.services.entities.ArtistService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/artists")
public class ArtistController {

  private final ArtistService artistService;

  @Autowired
  public ArtistController(ArtistService artistService) {
    this.artistService = artistService;
  }

  @GetMapping
  public ResponseEntity<ArtistGetAllResponse> getAllArtists() {
    List<Artist> artists = artistService.findAll();
    return new ResponseEntity<ArtistGetAllResponse> (
            new ArtistGetAllResponse(artists),
            HttpStatus.OK
    );
  }

  @PostMapping(value="byAlbumIds")
  public ResponseEntity<ArtistGetByAlbumIdsResponse> getArtistsByAlbumIds(@RequestBody ArtistGetByAlbumIdsDto dto) {
    List<Artist> artists = artistService.findByAlbumIds(dto.albumIds());
    return new ResponseEntity<ArtistGetByAlbumIdsResponse>(
        new ArtistGetByAlbumIdsResponse(artists, dto.albumIds()),
        HttpStatus.OK
    );
  }

  @PostMapping(value = "byPlaylistIds")
  public ResponseEntity<ArtistGetByPlaylistIdsResponse> getArtistsByPlaylistIds(@RequestBody ArtistGetByPlaylistIdsDto dto) {
    List<Artist> artists = artistService.findByPlaylistIds(dto.playlistIds());
    return new ResponseEntity<ArtistGetByPlaylistIdsResponse>(
            new ArtistGetByPlaylistIdsResponse(artists, dto.playlistIds()),
            HttpStatus.OK
    );
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<ArtistEntityResponse> getArtistById(@PathVariable("id") Long id) {
    Optional<Artist> artist = artistService.findById(id);
    if (artist.isPresent()) {
      ResponseEntity<ArtistEntityResponse> resEnt = new ResponseEntity<>(
          new ArtistEntityResponse(artist.get()),
          HttpStatus.OK);
      return resEnt;
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping(value="/{id}/albums")
  public ResponseEntity<ArtistAlbumsResponse> getArtistAlbums(@PathVariable("id") Long id) {
    Optional<Artist> artist = artistService.findById(id);
    if (!artist.isPresent()) {
      return ResponseEntity.notFound().build();
    } else {
      ResponseEntity<ArtistAlbumsResponse> resEnt = new ResponseEntity<>(
          new ArtistAlbumsResponse(artist.get()),
          HttpStatus.OK);
      return resEnt;
    }
  }

  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createArtist(@RequestBody ArtistCreateDto dto) {
    Artist artist = new Artist(dto.name());
    Artist artistSaved = artistService.save(artist);

    URI uri = HttpHelper.uri("/artists/" + artistSaved.getId());
    return ResponseEntity.created(uri).body(new ArtistEntityResponse(artistSaved));
  }

  // TODO: why am I getting a 415 on this one all of a sudden?
  @PostMapping(value = "/bulk/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ArtistBulkCreateResponse> createArtists(@RequestBody ArtistBulkCreateDto dto) {
    List<Artist> createdArtists = artistService.createAllFromDto(dto);
    return ResponseEntity.ok().body(new ArtistBulkCreateResponse(createdArtists));
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<ArtistEntityResponse> updateArtist(@PathVariable("id") Long id, @RequestBody ArtistEntityDto dto) {
    Optional<Artist> loadedArtist = artistService.findById(id);
    if (!loadedArtist.isPresent()){
      return ResponseEntity.notFound().build();
    } else {
      Artist updatedArtist = artistService.update(id, dto);
      return ResponseEntity.ok().body(new ArtistEntityResponse(updatedArtist));
    }
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<ArtistDeleteResponse> deleteArtist(@PathVariable("id") Long id) {
    artistService.deleteById(id);
    return ResponseEntity.ok().body(new ArtistDeleteResponse(id));
  }

  @GetMapping(value = "/{id}/tracks")
  public ResponseEntity<ArtistTracksResponse> getArtistTracks(@PathVariable("id") Long id) {
    Optional<Artist> artist = artistService.findById(id);
    if (!artist.isPresent()) {
      return ResponseEntity.notFound().build();
    } else {
      ResponseEntity<ArtistTracksResponse> resEnt = new ResponseEntity<>(
          new ArtistTracksResponse(artist.get()),
          HttpStatus.OK);
      return resEnt;
    }
  }
}
