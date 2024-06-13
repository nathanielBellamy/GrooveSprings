package dev.nateschieber.groovesprings.rest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.nateschieber.groovesprings.helpers.HttpHelper;
import dev.nateschieber.groovesprings.rest.responses.AbstractClient;


public class TrackClient extends AbstractClient {

    public TrackClient() {
        // through desktop proxy
        super("http://localhost:5678/api/v1/tracks");
    }

    private HttpRequest tracksCreateRequest(Object bodyObj) {
        URI uri = HttpHelper.uri(baseUrl);
        HttpRequest.BodyPublisher body = toPostBody(bodyObj);
        return HttpRequest.newBuilder()
                .uri(uri)
                .header("content-type", "application/json")
                .POST(body)
                .build();
    }

    public boolean bulkCreate(List<LocalTrackCreateDto> tracks) {
        HttpRequest request = tracksCreateRequest(tracks);

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("\n bulk create \n");
            System.out.println(response);
            return response.statusCode() == 200;
        } catch (Exception ex) {
            return false;
        }
    }
}
