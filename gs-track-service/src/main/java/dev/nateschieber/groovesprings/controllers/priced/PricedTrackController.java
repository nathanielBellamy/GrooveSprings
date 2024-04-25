package dev.nateschieber.groovesprings.controllers.priced;

import dev.nateschieber.groovesprings.entities.priced.PricedTrack;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import dev.nateschieber.groovesprings.rest.responses.priced.pricedTrack.AllPricedTracksResponse;
import dev.nateschieber.groovesprings.rest.responses.priced.pricedTrack.PricedTrackEntityResponse;
import dev.nateschieber.groovesprings.rest.responses.priced.pricedTrack.PricedTracksByAudioCodecResponse;
import dev.nateschieber.groovesprings.rest.responses.priced.pricedTrack.PricedTracksByReleaseYearResponse;
import dev.nateschieber.groovesprings.services.pricedEntities.PricedTrackService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pricedTracks")
public class PricedTrackController {

  private PricedTrackService pricedTrackService;

  @Autowired
  public PricedTrackController(PricedTrackService pricedTrackService) {
    this.pricedTrackService = pricedTrackService;
  }

  @GetMapping("")
  public ResponseEntity getAllPricedTracks() {
    List<PricedTrack> pricedTracks = pricedTrackService.findAll();
    ResponseEntity<AllPricedTracksResponse> resEnt = new ResponseEntity<>(
        new AllPricedTracksResponse(pricedTracks),
        HttpStatus.OK
    );
    return resEnt;
  }

  @GetMapping("/{id}")
  public ResponseEntity getPricedTrackById(@PathVariable Long id) {
    Optional<PricedTrack> pricedTrack = pricedTrackService.findById(id);
    if (!pricedTrack.isPresent()) {
      return ResponseEntity.notFound().build();
    } else {
      ResponseEntity<PricedTrackEntityResponse> resEnt = new ResponseEntity<>(
          new PricedTrackEntityResponse(pricedTrack.get()),
          HttpStatus.OK
      );
      return resEnt;
    }
  }

  @GetMapping("/year")
  public ResponseEntity getPricedTracksByReleaseYear(@RequestParam Integer year) {
    List<PricedTrack> pricedTracks = pricedTrackService.findByReleaseYear(year);
    ResponseEntity<PricedTracksByReleaseYearResponse> resEnt = new ResponseEntity<>(
        new PricedTracksByReleaseYearResponse(year, pricedTracks),
        HttpStatus.OK
    );
    return resEnt;
  }

  @GetMapping("/audiocodec")
  public ResponseEntity getPricedTracksByReleaseYear(@RequestParam AudioCodec audioCodec) {
    List<PricedTrack> pricedTracks = pricedTrackService.findByAudioCodec(audioCodec);
    ResponseEntity<PricedTracksByAudioCodecResponse> resEnt = new ResponseEntity<>(
        new PricedTracksByAudioCodecResponse(audioCodec, pricedTracks),
        HttpStatus.OK
    );
    return resEnt;
  }
}
