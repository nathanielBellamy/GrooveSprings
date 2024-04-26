package dev.nateschieber.groovesprings.services.entities;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import java.util.List;
import java.util.Optional;

public interface ITrackService<TRACK, UPDATE_DTO, CREATE_DTO> {
  List<TRACK> findAll();

  Optional<TRACK> findById(Long id);

  void deleteById(Long id);

  TRACK update(UPDATE_DTO dto);

  TRACK createFromDto(CREATE_DTO dto);

  TRACK save(Track track);

  List<TRACK> saveAll(List<Track> tracks);

  List<TRACK> findByReleaseYear(int year);

  List<TRACK> findByAudioCodec(AudioCodec audioCodec);

  List<TRACK> findByDurationBetween(Long min, Long max);
}
