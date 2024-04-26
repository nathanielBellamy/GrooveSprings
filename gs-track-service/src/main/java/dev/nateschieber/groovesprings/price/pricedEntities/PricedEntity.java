package dev.nateschieber.groovesprings.price.pricedEntities;

import dev.nateschieber.groovesprings.price.Price;

public abstract class PricedEntity {

  private Price price;

  public PricedEntity() {}

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
