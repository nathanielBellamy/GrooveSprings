package dev.nateschieber.groovesprings.services.priced;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import dev.nateschieber.groovesprings.controllers.mockData.price.MockPriceFactory;
import dev.nateschieber.groovesprings.controllers.mockData.priced.track.MockPricedTrackFactory;
import dev.nateschieber.groovesprings.controllers.mockData.track.MockTrackFactory;
import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.matchers.price.PriceMatcher;
import dev.nateschieber.groovesprings.matchers.track.TrackMatcher;
import dev.nateschieber.groovesprings.price.Price;
import dev.nateschieber.groovesprings.price.pricedEntities.PricedTrack;
import dev.nateschieber.groovesprings.rest.clients.PriceClient;
import dev.nateschieber.groovesprings.services.entities.TrackService;
import dev.nateschieber.groovesprings.services.pricedEntities.PricedTrackService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class PricedTrackServiceTest {

  @Autowired
  private TrackService trackService;

  @Autowired
  private PricedTrackService pricedTrackService;

  @MockBean
  private PriceClient priceClient;

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

  @Test
  public void PricedTrackService_findById_returnsPricedTrackById() throws Exception {
    Track mockTrack = MockTrackFactory.defaultTracks().get(0);
    Price mockPrice = MockPriceFactory.defaultTrackPrices().get(0);
    doReturn(Optional.of(mockPrice)).when(priceClient).getTrackPrice(any(Track.class));

    Optional<PricedTrack> pricedTrack = pricedTrackService.findById(1l);

    if (!pricedTrack.isPresent()) {
      throw new Exception("Unable to load Mock Priced Track - Check your Mocking and Mock Data");
    } else {
      PricedTrack pt = pricedTrack.get();
      assertEquals(mockTrack.getId(), pt.getTrack().getId());
      assertEquals(mockTrack.getTitle(), pt.getTrack().getTitle());
      assertEquals(mockPrice.getId(), pt.getPrice().getId());
      assertEquals(mockPrice.getUsdCents(), pt.getPrice().getUsdCents());
    }
  }

  @Test
  public void PricedTrackService_findAll_returnsAllPricedTracks() throws Exception {
    List<Track> mockTracks = MockTrackFactory.defaultTracks();
    List<Track> savedTracks = trackService.saveAll(mockTracks);

    List<Price> mockPrices = MockPriceFactory.defaultTrackPrices();

    doReturn(mockPrices).when(priceClient).getTrackPrices(any());

    List<PricedTrack> pts = pricedTrackService.findAll();
    assertTrue( new TrackMatcher(pts.get(0).getTrack()).matches(savedTracks.get(0)));
    assertTrue( new TrackMatcher(pts.get(1).getTrack()).matches(savedTracks.get(1)));
    assertTrue( new TrackMatcher(pts.get(2).getTrack()).matches(savedTracks.get(2)));

    assertTrue( new PriceMatcher(pts.get(0).getPrice()).matches(mockPrices.get(0)));
    assertTrue( new PriceMatcher(pts.get(1).getPrice()).matches(mockPrices.get(1)));
    assertTrue( new PriceMatcher(pts.get(2).getPrice()).matches(mockPrices.get(2)));
  }
}
