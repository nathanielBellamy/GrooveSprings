package dev.nateschieber.groovesprings.controllers;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;

@WebMvcTest(controllers = TrackController.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class AlbumControllerTest {
  // TODO
}
