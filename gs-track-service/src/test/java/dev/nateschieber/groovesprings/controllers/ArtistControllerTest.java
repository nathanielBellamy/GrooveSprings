package dev.nateschieber.groovesprings.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.nateschieber.groovesprings.mockData.artist.MockArtistFactory;
import dev.nateschieber.groovesprings.mockData.track.MockTrackFactory;
import dev.nateschieber.groovesprings.services.entities.ArtistService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = ArtistController.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ArtistControllerTest {
  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  private ArtistService artistService;

  @Test
  @DisplayName("ArtistController#GET/{id} -> FindsById")
  void ArtistController_GetArtist_ReturnsArtist () throws Exception {
    doReturn(
        Optional.of(MockArtistFactory.defaultArtists().get(0))
    ).when(artistService).findById(1l);

    ResultActions res = mockMvc.perform(get("/api/v1/artists/1"));
    res.andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.data.artist.name", is("Beach House")));
  }

  @Test
  @DisplayName("ArtistController#GET/{id} -> Returns404WhenNotFound")
  void ArtistController_GetArtist_Returns404WhenArtistNotFound () throws Exception {
    doReturn(
        Optional.empty()
    ).when(artistService).findById(1l);

    ResultActions res = mockMvc.perform(get("/api/v1/artists/1"));
    res.andExpect(status().isNotFound());
  }
}
