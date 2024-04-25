package dev.nateschieber.groovesprings.controllers.mockData.track;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import dev.nateschieber.groovesprings.enums.Genre;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MockTrackFactory {

  public static List<Track> defaultTracks() {
    return List.of(
        new Track(
            1l,
            Collections.emptyList(),
            Optional.empty(),
            "My Fight Song",
            1,
            123000l,
            AudioCodec.FLAC,
            List.of(Genre.PUNK),
            LocalDate.of(2022, 4, 4)),
        new Track(
            2l,
            Collections.emptyList(),
            Optional.empty(),
            "Rhythm of the Night",
            1,
            123000l,
            AudioCodec.WAV,
            List.of(Genre.EDM, Genre.ELECTRONIC),
            LocalDate.of(1995, 2, 12)),
        new Track(
            3l,
            Collections.emptyList(),
            Optional.empty(),
            "Walk The Line",
            1,
            456000l,
            AudioCodec.MP3_320,
            List.of(Genre.COUNTRY),
            LocalDate.of(1967, 2, 12))
    );
  }
}
