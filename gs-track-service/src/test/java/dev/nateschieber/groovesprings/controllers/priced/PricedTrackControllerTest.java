package dev.nateschieber.groovesprings.controllers.priced;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.nateschieber.groovesprings.mockData.priced.track.MockPricedTrackFactory;
import dev.nateschieber.groovesprings.services.pricedEntities.PricedTrackService;
import java.util.ArrayList;
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

@WebMvcTest(controllers = PricedTrackController.class)
@ExtendWith(MockitoExtension.class)
public class PricedTrackControllerTest {
  @Autowired
  MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private PricedTrackService pricedTrackService;

  @Test
  @DisplayName("PricedTrackController#GET -> FindsAll")
  void PricedTrackController_GetAllPricedTracks_ReturnsListOfAllPricedTracks () throws Exception {
    doReturn(
        MockPricedTrackFactory.defaultPricedTracks()
    ).when(pricedTrackService).findAll();

    ResultActions res = mockMvc.perform(get("/api/v1/priced/tracks"));
    res.andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.data.count", is(3)))
        .andExpect(jsonPath("$.data.pricedTracks", isA(ArrayList.class)))
        .andExpect(jsonPath("$.data.pricedTracks", hasSize(3)));
  }
}
