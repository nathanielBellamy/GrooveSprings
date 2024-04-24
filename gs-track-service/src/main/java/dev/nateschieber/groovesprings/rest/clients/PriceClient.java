package dev.nateschieber.groovesprings.rest.clients;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.helpers.HttpHelper;
import java.net.URI;

public class PriceClient {

  private static URI uri = HttpHelper.uri("http://localhost:5174/api/v1/prices");

  public long getTrackPrice(Track track) {

    return 0l;
  }
}
