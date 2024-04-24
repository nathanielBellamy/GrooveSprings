package dev.nateschieber.groovesprings.services.pricedEntities;

import dev.nateschieber.groovesprings.entities.Price;
import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.entities.priced.PricedTrack;
import dev.nateschieber.groovesprings.rest.clients.PriceClient;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackCreateDto;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackEntityDto;
import dev.nateschieber.groovesprings.services.entities.ITrackService;
import dev.nateschieber.groovesprings.services.entities.TrackService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PricedTrackService implements ITrackService<PricedTrack, TrackEntityDto, TrackCreateDto> {

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
    return null;
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
  public PricedTrack update(Long id, TrackEntityDto trackEntityDto) {
    return null;
  }

  @Override
  public PricedTrack createFromDto(TrackCreateDto trackCreateDto) {
    return null;
  }

  @Override
  public PricedTrack save(Track track, long artistId) {
    return null;
  }

  @Override
  public List<PricedTrack> findByReleaseYear(int year) {
    return null;
  }
}
