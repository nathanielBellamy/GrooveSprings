package dev.nateschieber.groovesprings.rest.dtos.track;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import java.util.List;

public record TracksByAudioCodecDto(AudioCodec audioCodec, int count, List<Track> tracks) implements  TrackDto {
}
