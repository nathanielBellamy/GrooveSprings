package dev.nateschieber.groovesprings.price.pricedEntities;

import dev.nateschieber.groovesprings.price.Price;
import dev.nateschieber.groovesprings.entities.Track;

public class PricedTrack extends PricedEntity {

  private Track track;

  public PricedTrack( ) { }

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
