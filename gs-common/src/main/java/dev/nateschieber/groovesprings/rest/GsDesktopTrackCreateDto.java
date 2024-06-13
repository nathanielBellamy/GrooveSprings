package dev.nateschieber.groovesprings.rest;

import dev.nateschieber.groovesprings.enums.AudioCodec;

import java.nio.file.Path;
import java.util.List;

public record GsDesktopTrackCreateDto(
        Path         path,
        List<String> artistNames,
        String       albumTitle,
        String       trackTitle,
        int          trackNumber,
        Integer      trackLength,
        Integer      sampleRate,
        Long         bitRate,
        Boolean      isVariableBitRate,
        Boolean      isLossless,
        AudioCodec   audioCodec,
        String       releaseYear
) { }
