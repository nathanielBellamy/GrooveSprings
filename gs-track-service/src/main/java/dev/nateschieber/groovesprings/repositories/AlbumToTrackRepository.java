package dev.nateschieber.groovesprings.repositories;

import dev.nateschieber.groovesprings.joins.AlbumToTrack;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlbumToTrackRepository extends JpaRepository<AlbumToTrack, Long> {

  @Query(
      value = "SELECT att.trackId FROM AlbumToTrack att WHERE att.albumId = ?1",
      nativeQuery = true
  )
  Collection<Long> findTrackIdsByAlbumId(Long albumId);
}
