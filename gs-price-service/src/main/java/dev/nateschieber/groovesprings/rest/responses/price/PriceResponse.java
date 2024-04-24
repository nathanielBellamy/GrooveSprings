package dev.nateschieber.groovesprings.rest.responses.price;

import dev.nateschieber.groovesprings.rest.dtos.price.PriceDto;
import dev.nateschieber.groovesprings.rest.responses.GsHttpResponse;

public abstract class PriceResponse extends GsHttpResponse {

  private PriceDto data;

  public PriceDto getData() {
    return data;
  }

  public void setData(PriceDto data) {
    this.data = data;
  }
}
