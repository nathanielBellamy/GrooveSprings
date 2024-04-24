package dev.nateschieber.groovesprings.entities.priced;

import dev.nateschieber.groovesprings.entities.Price;
import dev.nateschieber.groovesprings.entities.Track;

public class PricedTrack extends PricedEntity {

  private Track track;

  public PricedTrack(Price price, Track track) {
    super(price);
    this.track = track;
  }

  public Track getTrack() {
    return track;
  }

  public void setTrack(Track track) {
    this.track = track;
  }
}
