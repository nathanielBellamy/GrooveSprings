package dev.nateschieber.groovesprings.controllers;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackCreateDto;
import dev.nateschieber.groovesprings.rest.responses.track.TrackEntityResponse;
import dev.nateschieber.groovesprings.rest.responses.track.TrackGetAllResponse;
import dev.nateschieber.groovesprings.services.TrackService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/tracks")
public class TrackController {

  private final TrackService trackService;

  @Autowired
  public TrackController(TrackService trackService) {
    this.trackService = trackService;
  }

  @GetMapping
  public ResponseEntity getAllTracks() {
    List<Track> tracks = trackService.findAll();
    return ResponseEntity.ok().body(new TrackGetAllResponse(tracks));
  }

  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createTrack(@RequestBody TrackCreateDto dto) {
    System.out.println("HERE HERE HERE ");
    Track track = new Track(dto.artistId(), dto.albumId(), dto.title(), dto.duration());
    Track trackSaved = trackService.save(track);

    URI uri;
    try {
      uri = new URI("/tracks/" + trackSaved.getId());
    } catch (URISyntaxException e) {
      uri = null;
    }
    return ResponseEntity.created(uri).body(new TrackEntityResponse(trackSaved));
  }
}
