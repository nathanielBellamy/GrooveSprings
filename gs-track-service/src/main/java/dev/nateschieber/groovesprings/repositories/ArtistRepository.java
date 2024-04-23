package dev.nateschieber.groovesprings.repositories;

import dev.nateschieber.groovesprings.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {}
