package dev.nateschieber.groovesprings.controllers;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.helpers.HttpHelper;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackCreateDto;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackEntityDto;
import dev.nateschieber.groovesprings.rest.responses.track.TrackDeleteResponse;
import dev.nateschieber.groovesprings.rest.responses.track.TrackEntityResponse;
import dev.nateschieber.groovesprings.rest.responses.track.TrackGetAllResponse;
import dev.nateschieber.groovesprings.services.TrackService;
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

  @GetMapping(value = "/{id}")
  public ResponseEntity getTrackById(@PathVariable Long id) {
    Optional<Track> adoption = trackService.findById(id);
    if (adoption.isPresent()) {
      ResponseEntity<TrackEntityResponse> resEnt = new ResponseEntity<>(
          new TrackEntityResponse(adoption.get()),
          HttpStatus.OK);
      return resEnt;
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createTrack(@RequestBody TrackCreateDto dto) {
    System.out.println("HERE HERE HERE ");
    Track track = new Track(dto.artistId(), dto.albumId(), dto.title(), dto.duration());
    Track trackSaved = trackService.save(track);

    URI uri = HttpHelper.uri(trackSaved.getId());
    return ResponseEntity.created(uri).body(new TrackEntityResponse(trackSaved));
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity updateTrack(@PathVariable Long id, @RequestBody TrackEntityDto dto) {
    Optional<Track> loadedTrack = trackService.findById(id);
    if (!loadedTrack.isPresent()){
      return ResponseEntity.notFound().build();
    } else {
      Track updatedTrack = trackService.update(id, dto);
      return ResponseEntity.ok().body(new TrackEntityResponse(updatedTrack));
    }
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity deleteTrack(@PathVariable Long id) {
    trackService.deleteById(id);
    return ResponseEntity.ok().body(new TrackDeleteResponse(id));
  }
}
