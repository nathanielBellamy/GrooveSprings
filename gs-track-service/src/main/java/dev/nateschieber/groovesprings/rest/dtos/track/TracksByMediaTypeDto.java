package dev.nateschieber.groovesprings.rest.dtos.track;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import java.util.List;

public record TracksByMediaTypeDto(AudioCodec audioCodec, List<Track> tracks, int count) implements  TrackDto {
}
