package dev.nateschieber.groovesprings.mockData.track;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import dev.nateschieber.groovesprings.enums.Genre;
import dev.nateschieber.groovesprings.mockData.artist.MockArtistFactory;
import java.time.LocalDate;
import java.util.List;

public class MockTrackFactory {

  public static List<Track> defaultTracks() {
    return List.of(
        new Track(
            1l,
            List.of(MockArtistFactory.defaultArtists().get(0)),
            null,
            "Norway",
            1,
            123000,
            AudioCodec.FLAC,
            List.of(Genre.INDIE),
            LocalDate.of(2022, 4, 4),
                null,
                null,
                null,
                null,
                null),
        new Track(
            2l,
            List.of(MockArtistFactory.defaultArtists().get(1)),
            null,
            "Nothing Can Change This Love",
            1,
            123000,
            AudioCodec.WAV,
            List.of(Genre.SOUL),
            LocalDate.of(1995, 2, 12),
                null,
                null,
                null,
                null,
                null),
        new Track(
            3l,
            List.of(MockArtistFactory.defaultArtists().get(2)),
            null,
            "Ghost Mouth",
            1,
            456000,
            AudioCodec.MP3_320,
            List.of(Genre.ROCK),
            LocalDate.of(1967, 2, 12),
                null,
                null,
                null,
                null,
                null)
    );
  }
}
