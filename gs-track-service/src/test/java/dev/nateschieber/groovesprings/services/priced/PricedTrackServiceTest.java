package dev.nateschieber.groovesprings.services.priced;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.nateschieber.groovesprings.controllers.mockData.price.MockPriceFactory;
import dev.nateschieber.groovesprings.controllers.mockData.track.MockTrackFactory;
import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.price.Price;
import dev.nateschieber.groovesprings.price.pricedEntities.PricedTrack;
import dev.nateschieber.groovesprings.services.pricedEntities.PricedTrackService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
public class PricedTrackServiceTest {

  @Autowired
  private PricedTrackService service;

  @Test
  public void PricedTrackService_zipTracksWithPrices_mapsTracksAndPricesToPricedTracks() throws Exception {
    List<Track> tracks = MockTrackFactory.defaultTracks();
    List<Price> prices = MockPriceFactory.defaultTrackPrices();

    List<PricedTrack> pricedTracks = PricedTrackService.zipTracksWithPrices(tracks, prices);

    assertEquals(3, pricedTracks.size());
    assertEquals(tracks.get(0), pricedTracks.get(0).getTrack());
    assertEquals(prices.get(0), pricedTracks.get(0).getPrice());
    assertEquals(tracks.get(1), pricedTracks.get(1).getTrack());
    assertEquals(prices.get(1), pricedTracks.get(1).getPrice());
    assertEquals(tracks.get(2), pricedTracks.get(2).getTrack());
    assertEquals(prices.get(2), pricedTracks.get(2).getPrice());
  }
}
