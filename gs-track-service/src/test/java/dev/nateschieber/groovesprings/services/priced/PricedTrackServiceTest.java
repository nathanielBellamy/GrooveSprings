package dev.nateschieber.groovesprings.services.priced;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import dev.nateschieber.groovesprings.entities.Artist;
import dev.nateschieber.groovesprings.mockData.artist.MockArtistFactory;
import dev.nateschieber.groovesprings.mockData.price.MockPriceFactory;
import dev.nateschieber.groovesprings.mockData.track.MockTrackFactory;
import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.matchers.priced.PricedTrackMatcher;
import dev.nateschieber.groovesprings.price.Price;
import dev.nateschieber.groovesprings.price.pricedEntities.PricedTrack;
import dev.nateschieber.groovesprings.rest.clients.PriceClient;
import dev.nateschieber.groovesprings.services.entities.ArtistService;
import dev.nateschieber.groovesprings.services.entities.TrackService;
import dev.nateschieber.groovesprings.services.pricedEntities.PricedTrackService;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class PricedTrackServiceTest {

  @Autowired
  private TrackService trackService;

  @Autowired
  private ArtistService artistService;

  @Autowired
  private PricedTrackService pricedTrackService;

  @MockBean
  private PriceClient priceClient;

  @Test
  @Disabled
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
  @Disabled
  public void PricedTrackService_findById_returnsPricedTrackById() throws Exception {
    Artist mockArtist = MockArtistFactory.defaultArtists().get(0);
    artistService.save(mockArtist);
    Track mockTrack = MockTrackFactory.defaultTracks().get(0);
    trackService.save(mockTrack);
    Price mockPrice = MockPriceFactory.defaultTrackPrices().get(0);
    doReturn(Optional.of(mockPrice)).when(priceClient).getTrackPrice(any(Track.class));

    Optional<PricedTrack> pricedTrack = pricedTrackService.findById(1l);

    if (!pricedTrack.isPresent()) {
      throw new Exception("Unable to load Mock Priced Track - Check your Mocking and Mock Data");
    } else {
      PricedTrack pt = pricedTrack.get();
      assertTrue( new PricedTrackMatcher(pt).matches(new PricedTrack(mockPrice, mockTrack)));
    }
  }

  @Test
  @Disabled
  public void PricedTrackService_findAll_returnsAllPricedTracks() throws Exception {
    List<Artist> mockArtists = MockArtistFactory.defaultArtists();
    artistService.saveAll(mockArtists);
    List<Track> mockTracks = MockTrackFactory.defaultTracks();
    List<Track> savedTracks = trackService.saveAll(mockTracks);

    List<Price> mockPrices = MockPriceFactory.defaultTrackPrices();

    doReturn(mockPrices).when(priceClient).getTrackPrices(any());

    List<PricedTrack> pts = pricedTrackService.findAll();
    assertTrue( new PricedTrackMatcher(pts.get(0)).matches(new PricedTrack(mockPrices.get(0), savedTracks.get(0))));
    assertTrue( new PricedTrackMatcher(pts.get(1)).matches(new PricedTrack(mockPrices.get(1), savedTracks.get(1))));
    assertTrue( new PricedTrackMatcher(pts.get(2)).matches(new PricedTrack(mockPrices.get(2), savedTracks.get(2))));
  }
}
