package dev.nateschieber.groovesprings.helpers;

import java.net.URI;
import java.net.URISyntaxException;

public class TrackHelper {
  public static URI uri(Long id) {
    try {
      return new URI("/tracks/" + id);
    } catch (URISyntaxException e) {
      return null;
    }
  }
}
