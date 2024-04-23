package dev.nateschieber.groovesprings.repositories;

import dev.nateschieber.groovesprings.entities.Track;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrackRepository extends JpaRepository<Track, Long> {

  List<Track> findByReleaseDateBetween(LocalDate start, LocalDate end);
}
