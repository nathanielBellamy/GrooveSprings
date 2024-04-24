package dev.nateschieber.groovesprings.controllers;

import dev.nateschieber.groovesprings.entities.Price;
import dev.nateschieber.groovesprings.rest.dto.Track.TrackEntityDto;
import dev.nateschieber.groovesprings.rest.response.Track.TrackPriceResponse;
import dev.nateschieber.groovesprings.services.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/prices")
public class PriceController {

  private PriceService priceService;

  @Autowired
  public PriceController(PriceService priceService) {
    this.priceService = priceService;
  }

  @PostMapping(value = "/track", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity trackPrice(@RequestBody TrackEntityDto dto) {
    Price trackPrice = priceService.priceTrack(dto);
    ResponseEntity<TrackPriceResponse> resEnt = new ResponseEntity<>(
        new TrackPriceResponse(trackPrice),
        HttpStatus.OK
    );
    return resEnt;
  }
}
