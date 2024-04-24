package dev.nateschieber.groovesprings.entities.priced;

import dev.nateschieber.groovesprings.entities.Price;

public abstract class PricedEntity {

  private Price price;

  public PricedEntity(Price price) {
    this.price = price;
  }

  public Price getPrice() {
    return price;
  }

  public void setPrice(Price price) {
    this.price = price;
  }
}
