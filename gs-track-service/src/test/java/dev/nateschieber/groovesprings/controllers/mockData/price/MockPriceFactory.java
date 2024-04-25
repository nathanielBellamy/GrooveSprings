package dev.nateschieber.groovesprings.controllers.mockData.price;

import dev.nateschieber.groovesprings.enums.EntityType;
import dev.nateschieber.groovesprings.price.Price;
import java.time.LocalDateTime;
import java.util.List;

public class MockPriceFactory {

  public static List<Price> defaultTrackPrices() {
    return List.of(
        new Price(1, LocalDateTime.now(), EntityType.TRACK, 1, 199),
        new Price(2, LocalDateTime.now(), EntityType.TRACK, 2, 205),
        new Price(3, LocalDateTime.now(), EntityType.TRACK, 3, 305)
    );
  }
}
