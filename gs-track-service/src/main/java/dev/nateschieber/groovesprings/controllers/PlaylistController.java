package dev.nateschieber.groovesprings.controllers;

import dev.nateschieber.groovesprings.entities.Playlist;
import dev.nateschieber.groovesprings.helpers.HttpHelper;
import dev.nateschieber.groovesprings.rest.dtos.playlist.PlaylistCreateDto;
import dev.nateschieber.groovesprings.rest.responses.playlist.PlaylistEntityResponse;
import dev.nateschieber.groovesprings.rest.responses.playlist.PlaylistGetAllResponse;
import dev.nateschieber.groovesprings.rest.responses.playlist.PlaylistTracksResponse;
import dev.nateschieber.groovesprings.services.entities.PlaylistService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/playlists")
public class PlaylistController {

  private final PlaylistService playlistService;

  @Autowired
  protected PlaylistController(PlaylistService playlistService) {
    this.playlistService = playlistService;
  }

  @GetMapping
  public ResponseEntity getAllPlaylists() {
    List<Playlist> playlists = playlistService.findAll();

    return ResponseEntity.ok().body(new PlaylistGetAllResponse(playlists));
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity getPlaylistById(@PathVariable("id") Long id) {
    Optional<Playlist> playlist = playlistService.findById(id);
    if (playlist.isPresent()) {
      ResponseEntity<PlaylistEntityResponse> resEnt = new ResponseEntity<>(
          new PlaylistEntityResponse(playlist.get()),
          HttpStatus.OK);
      return resEnt;
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping(value = "/{id}/tracks")
  public ResponseEntity getTracksByPlaylistId(@PathVariable("id") Long id) {
    Optional<Playlist> playlistOpt = playlistService.findById(id);
    if (!playlistOpt.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    Playlist playlist = playlistOpt.get();

    return ResponseEntity.ok().body(new PlaylistTracksResponse(playlist));
  }

  @PostMapping
  public ResponseEntity createPlaylist(@RequestBody PlaylistCreateDto dto) {
    Playlist playlistSaved = playlistService.createFromDto(dto);
    URI uri = HttpHelper.uri("/api/v1/playlists/" + playlistSaved.getId());
    return ResponseEntity.created(uri).body(new PlaylistEntityResponse(playlistSaved));
  }
}
