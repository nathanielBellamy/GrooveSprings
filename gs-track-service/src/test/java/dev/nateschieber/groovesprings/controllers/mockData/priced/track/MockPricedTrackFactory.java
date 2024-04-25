package dev.nateschieber.groovesprings.controllers.mockData.priced.track;

import dev.nateschieber.groovesprings.controllers.mockData.price.MockPriceFactory;
import dev.nateschieber.groovesprings.controllers.mockData.track.MockTrackFactory;
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
