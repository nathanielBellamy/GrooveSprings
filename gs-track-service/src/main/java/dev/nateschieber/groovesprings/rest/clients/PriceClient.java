package dev.nateschieber.groovesprings.rest.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.helpers.HttpHelper;
import dev.nateschieber.groovesprings.rest.dtos.price.PriceEntityDto;
import dev.nateschieber.groovesprings.rest.responses.price.TrackPriceResponse;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class PriceClient {

  private static String baseUrl = "http://localhost:5174/api/v1/prices";
  private static ObjectMapper objectMapper;

  static {
    ObjectMapper om = new ObjectMapper();
    om.registerModule(new JavaTimeModule());
    objectMapper = om;
  }

  private static HttpRequest.BodyPublisher toPostBody(Object object) {
    ObjectWriter ow = objectMapper.writer();
    String json;
    try {
      json = ow.writeValueAsString(object);
    } catch (JsonProcessingException ex) {
      json = "{}";
    }
    return HttpRequest.BodyPublishers.ofString(json);
  }

  public Optional<Long> getTrackPrice(Track track) {
    HttpClient client = HttpClient.newHttpClient();
    URI uri = HttpHelper.uri(PriceClient.baseUrl + "/track");
    HttpRequest.BodyPublisher body = toPostBody(track);
    HttpRequest request = HttpRequest.newBuilder()
        .uri(uri)
        .header("content-type", "application/json")
        .POST(body)
        .build();

    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 200){
        JsonNode respNode = objectMapper.readValue(response.body(), JsonNode.class);
        JsonNode priceNode = respNode.get("data").get("price");
        PriceEntityDto dto = objectMapper.convertValue(priceNode, PriceEntityDto.class);

        return Optional.of(dto.usdCents());
      } else {
        return Optional.empty();
      }
    } catch (Exception ex) {
      return Optional.empty();
    }
  }
}
