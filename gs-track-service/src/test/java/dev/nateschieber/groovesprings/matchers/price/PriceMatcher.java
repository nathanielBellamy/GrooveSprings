package dev.nateschieber.groovesprings.matchers.price;

import dev.nateschieber.groovesprings.price.Price;
import org.mockito.ArgumentMatcher;

public class PriceMatcher implements ArgumentMatcher<Price> {

  private Price left;

  public PriceMatcher(Price price) { this.left = price; }

  @Override
  public boolean matches(Price right) {
    return left.getId() == right.getId()
        && left.getEntityType().equals(right.getEntityType())
        && left.getEntityId() == right.getEntityId()
        && left.getAt().equals(right.getAt())
        && left.getUsdCents() == right.getUsdCents();
  }
}
