package dev.nateschieber.groovesprings.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.nateschieber.groovesprings.mockData.track.MockTrackFactory;
import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackCreateDto;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackEntityDto;
import dev.nateschieber.groovesprings.services.entities.TrackService;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = TrackController.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class TrackControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  private TrackService trackService;

  @Test
  @DisplayName("TrackController#GET/{id} -> FindsById")
  void TrackController_GetTrack_ReturnsTrack () throws Exception {
    doReturn(
        Optional.of(MockTrackFactory.defaultTracks().get(0))
    ).when(trackService).findById(1l);

    ResultActions res = mockMvc.perform(get("/api/v1/tracks/1"));
    res.andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.data.track.title", is("My Fight Song")))
        .andExpect(jsonPath("$.data.track.genres", hasSize(1)));
  }

  @Test
  @DisplayName("TrackController#GET/{id} -> NotFound -> returns 404")
  void TrackController_GetTrack_Returns404WhenNotFound () throws Exception {
    doReturn(Optional.empty()).when(trackService).findById(1l);

    ResultActions res = mockMvc.perform(get("/api/v1/tracks/1"));
    res.andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("TrackController#POST -> CreatesTrack")
  void TrackController_POST_CreatesTrack () throws Exception {
    Track mockTrack = MockTrackFactory.defaultTracks().get(0);

    doReturn(mockTrack).when(trackService).createFromDto(ArgumentMatchers.<TrackCreateDto>any());

    String jsonPayload = objectMapper.writer().writeValueAsString(
        new TrackCreateDto(
            mockTrack.getArtists().stream().map(a -> a.getId()).toList(),
            null,
            mockTrack.getTitle(),
            mockTrack.getTrackNumber(),
            mockTrack.getDuration(),
            mockTrack.getAudioCodec(),
            mockTrack.getGenres(),
            mockTrack.getReleaseDate()
        )
    );

    ResultActions res = mockMvc.perform(
        post("/api/v1/tracks/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonPayload)
    ).andExpect(status().isCreated());
  }

  @Test
  @DisplayName("TrackController#POST -> Rejects Track with Empty String Title")
  void TrackController_POST_ValidatesNonEmptyStringTitle () throws Exception {
    Track mockTrack = MockTrackFactory.defaultTracks().get(0);
    mockTrack.setTitle("");

    doReturn(mockTrack).when(trackService).createFromDto(ArgumentMatchers.<TrackCreateDto>any());

    String jsonPayload = objectMapper.writer().writeValueAsString(
      new TrackCreateDto(
          mockTrack.getArtists().stream().map(a -> a.getId()).toList(),
          null,
          "", // Should reject empty Title
          mockTrack.getTrackNumber(),
          mockTrack.getDuration(),
          mockTrack.getAudioCodec(),
          mockTrack.getGenres(),
          mockTrack.getReleaseDate()
      )
    );

    ResultActions res = mockMvc.perform(
        post("/api/v1/tracks/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonPayload)
    ).andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("TrackController#POST -> Rejects Track with Negative Duration")
  void TrackController_POST_ValidatesPositveDuration () throws Exception {
    Track mockTrack = MockTrackFactory.defaultTracks().get(0);
    mockTrack.setDuration(-10l);

    doReturn(mockTrack).when(trackService).createFromDto(ArgumentMatchers.<TrackCreateDto>any());

    String jsonPayload = objectMapper.writer().writeValueAsString(
        new TrackCreateDto(
            mockTrack.getArtists().stream().map(a -> a.getId()).toList(),
            null,
            mockTrack.getTitle(),
            mockTrack.getTrackNumber(),
            mockTrack.getDuration(),
            mockTrack.getAudioCodec(),
            mockTrack.getGenres(),
            mockTrack.getReleaseDate()
        )
    );

    ResultActions res = mockMvc.perform(
        post("/api/v1/tracks/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonPayload)
    ).andExpect(status().isBadRequest());
  }
}
