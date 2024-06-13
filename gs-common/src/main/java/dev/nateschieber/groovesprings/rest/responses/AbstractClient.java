package dev.nateschieber.groovesprings.rest.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class AbstractClient {
    protected String baseUrl;
    protected static ObjectMapper objectMapper;
    protected HttpClient client = HttpClient.newHttpClient();

    static {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        objectMapper = om;
    }

    public AbstractClient() {}

    public AbstractClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    protected static HttpRequest.BodyPublisher toPostBody(Object object) {
        ObjectWriter ow = objectMapper.writer();
        String json;
        try {
            json = ow.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            json = "{}";
        }
        return HttpRequest.BodyPublishers.ofString(json);
    }

}
