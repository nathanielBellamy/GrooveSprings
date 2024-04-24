package dev.nateschieber.groovesprings.rest.responses.Price;

import dev.nateschieber.groovesprings.entities.Price;
import dev.nateschieber.groovesprings.rest.dtos.Price.AllPricesDto;
import java.util.List;

public class AllPricesResponse extends PriceResponse {
  public AllPricesResponse(List<Price> prices) {
    this.setData(new AllPricesDto(prices));
  }
}
