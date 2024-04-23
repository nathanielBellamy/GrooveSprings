package dev.nateschieber.groovesprings.repositories;

import dev.nateschieber.groovesprings.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> { }
