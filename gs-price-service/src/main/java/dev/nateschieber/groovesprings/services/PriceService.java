package dev.nateschieber.groovesprings.services;

import dev.nateschieber.groovesprings.entities.Price;
import dev.nateschieber.groovesprings.enums.EntityType;
import dev.nateschieber.groovesprings.repositories.PriceRepository;
import dev.nateschieber.groovesprings.rest.dto.Track.TrackEntityDto;
import enums.Genre;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
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
    //  - sales/discount by Duration
    long duration = dto.duration();
    // TODO:
    //   - sales/discount by Genre
    List<Genre> genres = dto.genres();
    // TODO
    //   - sales/discount by ReleaseDate
    LocalDate releaseDate = dto.releaseDate();
    return new Price(
        EntityType.TRACK,
        LocalDateTime.now(),
        dto.id(),
        priceFunction(duration, genres, releaseDate));
  }

  private long priceFunction(long duration, List<Genre> genres, LocalDate releaseDate) {

    double durationFactor = ThreadLocalRandom.current().nextDouble() * (duration / 10000d);
    double genresFactor = ThreadLocalRandom.current().nextDouble();
    double releaseDateFactor = ThreadLocalRandom.current().nextDouble() * (releaseDate.getYear() / releaseDate.getDayOfMonth());

    return (long) Math.floor((durationFactor * genresFactor * releaseDateFactor) * 10d);
  }
}
