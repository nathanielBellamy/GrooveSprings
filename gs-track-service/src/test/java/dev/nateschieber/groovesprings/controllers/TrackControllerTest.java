package dev.nateschieber.groovesprings.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import dev.nateschieber.groovesprings.services.entities.TrackService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = TrackController.class)
@ExtendWith(MockitoExtension.class)
public class TrackControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private TrackService trackService;

  @Test
  @DisplayName("TrackController#GET -> FindsAll")
  void TrackController_GetAllTracks_ReturnsListOfAllTracks () throws Exception {
    List<Track> mockTracks = List.of(
        new Track(Collections.emptyList(), Optional.empty(), "My Fight Song", 1, 123000l, AudioCodec.FLAC, LocalDate.of(2024, 4, 4))
    );

    doReturn(mockTracks).when(trackService).findAll();

    ResultActions res = mockMvc.perform(get("/api/v1/tracks"));
    res.andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.data.tracks", isA(ArrayList.class)))
        .andExpect(jsonPath("$.data.tracks", hasSize(1)))
        .andExpect(jsonPath("$.data.tracks[0].title", is("My Fight Song")));
  }

}
