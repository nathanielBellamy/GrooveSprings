package dev.nateschieber.groovesprings.controllers;

import dev.nateschieber.groovesprings.entities.Album;
import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import dev.nateschieber.groovesprings.helpers.HttpHelper;
import dev.nateschieber.groovesprings.rest.dtos.album.AlbumCreateDto;
import dev.nateschieber.groovesprings.rest.dtos.album.AlbumEntityDto;
import dev.nateschieber.groovesprings.rest.responses.album.AlbumDeleteResponse;
import dev.nateschieber.groovesprings.rest.responses.album.AlbumEntityResponse;
import dev.nateschieber.groovesprings.rest.responses.album.AlbumGetAllResponse;
import dev.nateschieber.groovesprings.rest.responses.album.AlbumTracksResponse;
import dev.nateschieber.groovesprings.services.entities.AlbumService;
import jakarta.validation.constraints.Null;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/albums")
public class AlbumController {

  private final AlbumService albumService;

  @Autowired
  public AlbumController(
      AlbumService albumService) {
    this.albumService = albumService;
  }

  @GetMapping
  public ResponseEntity getAllAlbums() {
    List<Album> albums = albumService.findAll();
    return ResponseEntity.ok().body(new AlbumGetAllResponse(albums));
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity getAlbumById(@PathVariable("id") Long id) {
    Optional<Album> album = albumService.findById(id);
    if (!album.isPresent()) {
      return ResponseEntity.notFound().build();
    } else {
      ResponseEntity<AlbumEntityResponse> resEnt = new ResponseEntity<>(
          new AlbumEntityResponse(album.get()),
          HttpStatus.OK);
      return resEnt;
    }
  }

  @GetMapping(value = "/{id}/tracks")
  public ResponseEntity getAlbumTracksByAlbumId(@PathVariable("id") Long id, @RequestParam(required = false) AudioCodec audioCodec) {
    Optional<Album> album = albumService.findById(id);
    if (!album.isPresent()) {
      return ResponseEntity.notFound().build();
    } else {
      List<Track> tracks;
      if (audioCodec != null) {
        tracks = album.get().getTracks().stream().filter(t -> t.getAudioCodec() == audioCodec).toList();
      } else {
        tracks = album.get().getTracks();
      }
      ResponseEntity<AlbumTracksResponse> resEnt = new ResponseEntity<>(
          new AlbumTracksResponse(
              album.get(),
              tracks
          ),
          HttpStatus.OK);
      return resEnt;
    }
  }

  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createAlbum(@RequestBody AlbumCreateDto dto) {
    Album albumSaved = albumService.createFromDto(dto);

    URI uri = HttpHelper.uri("/albums/" + albumSaved.getId());
    return ResponseEntity.created(uri).body(new AlbumEntityResponse(albumSaved));
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity updateAlbum(@PathVariable("id") Long id, @RequestBody AlbumEntityDto dto) {
    Optional<Album> loadedAlbum = albumService.findById(id);
    if (!loadedAlbum.isPresent()){
      return ResponseEntity.notFound().build();
    } else {
      Album updatedAlbum = albumService.update(id, dto);
      return ResponseEntity.ok().body(new AlbumEntityResponse(updatedAlbum));
    }
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity deleteAlbum(@PathVariable("id") Long id) {
    albumService.deleteById(id);
    return ResponseEntity.ok().body(new AlbumDeleteResponse(id));
  }
}
