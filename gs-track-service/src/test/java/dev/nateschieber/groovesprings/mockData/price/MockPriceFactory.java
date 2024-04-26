package dev.nateschieber.groovesprings.mockData.price;

import dev.nateschieber.groovesprings.enums.EntityType;
import dev.nateschieber.groovesprings.price.Price;
import java.time.LocalDateTime;
import java.util.List;

public class MockPriceFactory {

  public static List<Price> defaultTrackPrices() {
    return List.of(
        new Price(1l, LocalDateTime.now(), EntityType.TRACK, 1l, 199),
        new Price(2l, LocalDateTime.now(), EntityType.TRACK, 2l, 205),
        new Price(3l, LocalDateTime.now(), EntityType.TRACK, 3l, 305)
    );
  }
}
