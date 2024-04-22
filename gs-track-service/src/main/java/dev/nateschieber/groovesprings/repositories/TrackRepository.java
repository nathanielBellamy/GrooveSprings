package dev.nateschieber.groovesprings.repositories;

import dev.nateschieber.groovesprings.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long> {}
