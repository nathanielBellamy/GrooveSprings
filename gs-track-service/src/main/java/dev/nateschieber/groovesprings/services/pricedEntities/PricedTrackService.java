package dev.nateschieber.groovesprings.services.pricedEntities;

import dev.nateschieber.groovesprings.price.Price;
import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.price.pricedEntities.PricedTrack;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import dev.nateschieber.groovesprings.rest.clients.PriceClient;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackCreateDto;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackDto;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackEntityDto;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackUpdateDto;
import dev.nateschieber.groovesprings.services.entities.ITrackService;
import dev.nateschieber.groovesprings.services.entities.TrackService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PricedTrackService implements ITrackService<PricedTrack, TrackUpdateDto, TrackCreateDto> {

  private PriceClient priceClient;
  private TrackService trackService;

  @Autowired
  public PricedTrackService(PriceClient priceClient, TrackService trackService) {
    this.priceClient = priceClient;
    this.trackService = trackService;
  }

  @Override
  public List<PricedTrack> findAll() {
    List<Track> tracks = trackService.findAll();
    List<Price> prices = priceClient.getTrackPrices(tracks);
    return zipTracksWithPrices(tracks, prices);
  }

  @Override
  public Optional<PricedTrack> findById(Long id) {
    Optional<Track> track = trackService.findById(id);
    if (!track.isPresent()) {
      return Optional.empty();
    } else {
      Optional<Price> price = priceClient.getTrackPrice(track.get());
      if (!price.isPresent()) {
        return Optional.empty();
      } else {
        return Optional.of(new PricedTrack(price.get(), track.get()));
      }
    }
  }

  @Override
  public void deleteById(Long id) {
  }

  @Override
  public PricedTrack update(TrackUpdateDto trackUpdateDto) {
    return null;
  }

  @Override
  public PricedTrack createFromDto(TrackCreateDto trackCreateDto) {
    return null;
  }

  @Override
  public PricedTrack save(Track track) {
    return null;
  }

  @Override
  public List<PricedTrack> saveAll(List<Track> tracks) {
    return Collections.emptyList();
  }

  @Override
  public List<PricedTrack> findByReleaseYear(int year) {
    List<Track> tracksForYear = trackService.findByReleaseYear(year);
    List<Price> prices = priceClient.getTrackPrices(tracksForYear);
    return zipTracksWithPrices(tracksForYear, prices);
  }

  @Override
  public List<PricedTrack> findByAudioCodec(AudioCodec audioCodec) {
    List<Track> tracksInCodec = trackService.findByAudioCodec(audioCodec);
    List<Price> prices = priceClient.getTrackPrices(tracksInCodec);
    return zipTracksWithPrices(tracksInCodec, prices);
  }

  public static List<PricedTrack> zipTracksWithPrices(List<Track> tracks, List<Price> prices) {
    return tracks.stream().map(t -> {
      Optional<Price> price = prices.stream().filter(p -> p.getEntityId() == t.getId()).findFirst();
      if (!price.isPresent()) {
        return null;
      } else {
        return new PricedTrack(price.get(), t);
      }
    }).filter(pt -> pt != null).collect(Collectors.toList());
  }
}
