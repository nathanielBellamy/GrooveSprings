package dev.nateschieber.groovesprings.helpers;

import java.net.URI;
import java.net.URISyntaxException;

public class HttpHelper {
  public static URI uri(String uri) {
    try {
      return new URI(uri);
    } catch (URISyntaxException e) {
      return null;
    }
  }
}
