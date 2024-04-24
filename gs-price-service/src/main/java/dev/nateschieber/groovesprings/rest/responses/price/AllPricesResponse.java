package dev.nateschieber.groovesprings.rest.responses.price;

import dev.nateschieber.groovesprings.entities.Price;
import dev.nateschieber.groovesprings.rest.dtos.price.AllPricesDto;
import java.util.List;

public class AllPricesResponse extends PriceResponse {
  public AllPricesResponse(List<Price> prices) {
    this.setData(new AllPricesDto(prices));
  }
}
