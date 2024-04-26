package dev.nateschieber.groovesprings.repositories;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long> {

  List<Track> findByReleaseDateBetween(LocalDate start, LocalDate end);
  List<Track> findByAudioCodec(AudioCodec audioCodec);
  List<Track> findByDurationBetween(Long min, Long max);
}
