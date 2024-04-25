package dev.nateschieber.groovesprings.matchers.priced;

import dev.nateschieber.groovesprings.matchers.price.PriceMatcher;
import dev.nateschieber.groovesprings.matchers.track.TrackMatcher;
import dev.nateschieber.groovesprings.price.pricedEntities.PricedTrack;
import org.mockito.ArgumentMatcher;

public class PricedTrackMatcher implements ArgumentMatcher<PricedTrack> {

  private PricedTrack left;

  public PricedTrackMatcher(PricedTrack left) { this.left = left; }

  @Override
  public boolean matches(PricedTrack right) {
    boolean trackMatch = new TrackMatcher(left.getTrack()).matches(right.getTrack());
    boolean priceMatch = new PriceMatcher(left.getPrice()).matches(right.getPrice());

    return trackMatch && priceMatch;
  }
}
