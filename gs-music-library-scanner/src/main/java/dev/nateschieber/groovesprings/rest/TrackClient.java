package dev.nateschieber.groovesprings.rest;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import dev.nateschieber.groovesprings.helpers.HttpHelper;

public class TrackClient extends AbstractClient {

    public TrackClient() {
        super("http://localhost:5173/api/v1/desktop/bulkCreate");
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

    public boolean bulkCreate(List<GsDesktopTrackCreateDto> tracks) {
        HttpRequest request = tracksCreateRequest(tracks);
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 200;
        } catch (Exception ex) {
            return false;
        }
    }
}
