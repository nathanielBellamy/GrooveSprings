package dev.nateschieber.groovesprings.rest.clients;

import com.fasterxml.jackson.databind.JsonNode;
import dev.nateschieber.groovesprings.price.Price;
import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.helpers.HttpHelper;
import dev.nateschieber.groovesprings.rest.dtos.price.client.TrackPriceClientResponseDto;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackGetAllDto;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import dev.nateschieber.groovesprings.rest.AbstractClient;
import org.springframework.stereotype.Service;

@Service
public class PriceClient extends AbstractClient {

  public PriceClient() {}

  private HttpRequest priceRequest(Object bodyObj, String path) {
    URI uri = HttpHelper.uri(baseUrl + "/" + path);
    HttpRequest.BodyPublisher body = toPostBody(bodyObj);
    return HttpRequest.newBuilder()
        .uri(uri)
        .header("content-type", "application/json")
        .POST(body)
        .build();
  }

  public Optional<Price> getTrackPrice(Track track) {
    HttpRequest request = priceRequest(track, "track");

    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 200){
        TrackPriceClientResponseDto pcrd = objectMapper.readValue(response.body(), TrackPriceClientResponseDto.class);
        Price price = pcrd.data().price();

        return Optional.of(price);
      } else {
        return Optional.empty();
      }
    } catch (Exception ex) {
      return Optional.empty();
    }
  }

  public List<Price> getTrackPrices(List<Track> tracks) {

    HttpRequest request = priceRequest(new TrackGetAllDto(tracks.size(), tracks), "bulk/track");

    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 200) {
        JsonNode respNode = objectMapper.readValue(response.body(), JsonNode.class);
        JsonNode priceNode = respNode.get("data").get("prices");
        Price[] prices = objectMapper.convertValue(priceNode, Price[].class);

        return Arrays.asList(prices);
      } else {
        return Collections.emptyList();
      }
    } catch (Exception ex) {
      return Collections.emptyList();
    }
  }
}
