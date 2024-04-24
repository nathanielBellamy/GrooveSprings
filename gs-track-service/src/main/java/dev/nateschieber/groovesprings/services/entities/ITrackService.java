package dev.nateschieber.groovesprings.services.entities;

import dev.nateschieber.groovesprings.entities.Track;
import java.util.List;
import java.util.Optional;

public interface ITrackService<TRACK, ENT_DTO, CREATE_DTO> {
  List<TRACK> findAll();

  Optional<TRACK> findById(Long id);

  void deleteById(Long id);

  TRACK update(Long id, ENT_DTO dto);

  TRACK createFromDto(CREATE_DTO dto);

  TRACK save(Track track, long artistId);

  List<TRACK> findByReleaseYear(int year);
}
