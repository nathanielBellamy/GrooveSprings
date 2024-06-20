package dev.nateschieber.groovesprings.repositories;

import dev.nateschieber.groovesprings.entities.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
