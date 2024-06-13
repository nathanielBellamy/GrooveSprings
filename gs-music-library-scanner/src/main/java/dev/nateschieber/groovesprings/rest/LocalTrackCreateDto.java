package dev.nateschieber.groovesprings.rest;

import java.nio.file.Path;
import java.util.List;

public record LocalTrackCreateDto(
        Path path,
        List<String> artistNames,
        String albumTitle,
        String trackTitle,
        int    trackNumber,
        Integer trackLength,
        Integer sampleRate,
        Long bitRate,
        Boolean isVariableBitRate,
        Boolean isLoseless,
        String audioCodec
) { }
