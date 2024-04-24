package dev.nateschieber.groovesprings.services;

import dev.nateschieber.groovesprings.entities.Price;
import dev.nateschieber.groovesprings.repositories.PriceRepository;
import dev.nateschieber.groovesprings.rest.dto.Track.TrackEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

  private PriceRepository priceRepository;

  @Autowired
  public PriceService(PriceRepository priceRepository) {
    this.priceRepository = priceRepository;
  }

  public Price priceTrack(TrackEntityDto dto) {
    // TODO
    return new Price();
  }
}
