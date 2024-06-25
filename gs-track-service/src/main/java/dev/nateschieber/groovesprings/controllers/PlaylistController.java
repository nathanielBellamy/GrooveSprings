package dev.nateschieber.groovesprings.controllers;

import dev.nateschieber.groovesprings.entities.Playlist;
import dev.nateschieber.groovesprings.helpers.HttpHelper;
import dev.nateschieber.groovesprings.rest.dtos.playlist.PlaylistByArtistIdsDto;
import dev.nateschieber.groovesprings.rest.dtos.playlist.PlaylistCreateDto;
import dev.nateschieber.groovesprings.rest.dtos.playlist.PlaylistDto;
import dev.nateschieber.groovesprings.rest.dtos.playlist.PlaylistGetByAlbumIdsDto;
import dev.nateschieber.groovesprings.rest.dtos.playlist.PlaylistGetByArtistIdsDto;
import dev.nateschieber.groovesprings.rest.responses.playlist.PlaylistEntityResponse;
import dev.nateschieber.groovesprings.rest.responses.playlist.PlaylistGetAllResponse;
import dev.nateschieber.groovesprings.rest.responses.playlist.PlaylistGetByAlbumIdsResponse;
import dev.nateschieber.groovesprings.rest.responses.playlist.PlaylistGetByArtistIdsResponse;
import dev.nateschieber.groovesprings.rest.responses.playlist.PlaylistTracksResponse;
import dev.nateschieber.groovesprings.services.entities.PlaylistService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/playlists")
public class PlaylistController {

  private final PlaylistService playlistService;

  @Autowired
  protected PlaylistController(PlaylistService playlistService) {
    this.playlistService = playlistService;
  }

  @GetMapping
  public ResponseEntity<PlaylistGetAllResponse> getAllPlaylists() {
    List<Playlist> playlists = playlistService.findAll();

    return ResponseEntity.ok().body(new PlaylistGetAllResponse(playlists));
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<PlaylistEntityResponse> getPlaylistById(@PathVariable("id") Long id) {
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
  public ResponseEntity<PlaylistTracksResponse> getTracksByPlaylistId(@PathVariable("id") Long id) {
    Optional<Playlist> playlistOpt = playlistService.findById(id);
    if (playlistOpt.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    Playlist playlist = playlistOpt.get();

    return ResponseEntity.ok().body(new PlaylistTracksResponse(playlist));
  }

  @PostMapping
  public ResponseEntity<PlaylistEntityResponse> createPlaylist(@RequestBody PlaylistCreateDto dto) {
    Playlist playlistSaved = playlistService.createFromDto(dto);
    URI uri = HttpHelper.uri("/api/v1/playlists/" + playlistSaved.getId());
    return ResponseEntity.created(uri).body(new PlaylistEntityResponse(playlistSaved));
  }

  @PostMapping(value = "/byAlbumIds")
  public ResponseEntity<PlaylistGetByAlbumIdsResponse> getPlaylistByAlbumIds(@RequestBody PlaylistGetByAlbumIdsDto dto) {
    List<Playlist> playlists = playlistService.findByAlbumIds(dto.albumIds());
    ResponseEntity<PlaylistGetByAlbumIdsResponse> resEnt = new ResponseEntity<>(
        new PlaylistGetByAlbumIdsResponse(dto.albumIds(), playlists),
        HttpStatus.OK);
    return resEnt;
  }

  @PostMapping(value = "/byArtistIds")
  public ResponseEntity<PlaylistGetByArtistIdsResponse> getPlaylistByArtistIds(@RequestBody PlaylistGetByArtistIdsDto dto) {
    List<Playlist> playlists = playlistService.findByArtistIds(dto.artistIds());
    ResponseEntity<PlaylistGetByArtistIdsResponse> resEnt = new ResponseEntity<>(
        new PlaylistGetByArtistIdsResponse(dto.artistIds(), playlists),
        HttpStatus.OK);
    return resEnt;
  }
}
