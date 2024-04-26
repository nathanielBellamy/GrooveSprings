package dev.nateschieber.groovesprings.mockData.priced.track;

import dev.nateschieber.groovesprings.mockData.price.MockPriceFactory;
import dev.nateschieber.groovesprings.mockData.track.MockTrackFactory;
import dev.nateschieber.groovesprings.price.Price;
import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.price.pricedEntities.PricedTrack;
import dev.nateschieber.groovesprings.services.pricedEntities.PricedTrackService;
import java.util.List;

public class MockPricedTrackFactory {

  public static List<PricedTrack> defaultPricedTracks() {
    List<Track> tracks = MockTrackFactory.defaultTracks();
    List<Price> prices = MockPriceFactory.defaultTrackPrices();
    return PricedTrackService.zipTracksWithPrices(tracks, prices);
  }
}
