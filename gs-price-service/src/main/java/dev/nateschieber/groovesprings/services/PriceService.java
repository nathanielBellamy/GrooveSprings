package dev.nateschieber.groovesprings.services;

import dev.nateschieber.groovesprings.entities.Price;
import dev.nateschieber.groovesprings.enums.EntityType;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import dev.nateschieber.groovesprings.repositories.PriceRepository;
import dev.nateschieber.groovesprings.rest.dtos.album.AlbumEntityDto;
import dev.nateschieber.groovesprings.rest.dtos.artist.ArtistEntityDto;
import dev.nateschieber.groovesprings.enums.Genre;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackEntityBulkDto;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackEntityDto;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

  private PriceRepository priceRepository;

  @Autowired
  public PriceService(PriceRepository priceRepository) {
    this.priceRepository = priceRepository;
  }

  public List<Price> findAll() {
    return priceRepository.findAll();
  }

  // TODO
  //   - sales/discount by Duration
  //   - sales/discount by Genre
  //   - sales/discount by ReleaseDate
  public Price priceTrack(TrackEntityDto dto) {
    return priceRepository.save(
        new Price(
          EntityType.TRACK,
          dto.id(),
          trackPriceFunction(dto.duration(), dto.genres(), dto.releaseDate(), dto.audioCodec()))
    );
  }

  public List<Price> priceTracks(TrackEntityBulkDto dto) {
    var res = dto.tracks().stream().map(teDto -> priceTrack(teDto)).collect(Collectors.toList());
    return res;
  }

  private long trackPriceFunction(long duration, List<Genre> genres, LocalDate releaseDate, AudioCodec audioCodec) {

    double durationFactor = ThreadLocalRandom.current().nextDouble() * (duration / 10000d);
    double genresFactor = ThreadLocalRandom.current().nextDouble();
    double releaseDateFactor = ThreadLocalRandom.current().nextDouble() * (releaseDate.getYear() / releaseDate.getDayOfMonth());
    double audioCodecFactor = ThreadLocalRandom.current().nextDouble() * (audioCodec.getPriceFactor() / 10.0);

    return (long) Math.floor((durationFactor * genresFactor * releaseDateFactor * audioCodecFactor) / 10d);
  }

  public Price priceAlbum(AlbumEntityDto dto) {
    String name = dto.name();
    List<ArtistEntityDto> artists = dto.artists();
    LocalDate releaseDate = dto.releaseDate();
    List<TrackEntityDto> tracks = dto.tracks();

    // TODO

    long priceUsdCents = (long) Math.floor((ThreadLocalRandom.current().nextDouble() * 1699));

    return priceRepository.save(
        new Price(EntityType.ALBUM, dto.id(), priceUsdCents)
    );
  }


  public Price priceArtist(ArtistEntityDto dto) {

    // TODO

    long priceUsdCents = (long) Math.floor((ThreadLocalRandom.current().nextDouble() * 10000));

    return priceRepository.save(
        new Price(EntityType.ARTIST, dto.id(), priceUsdCents)
    );
  }
}
