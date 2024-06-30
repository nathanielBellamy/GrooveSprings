package dev.nateschieber.groovesprings.repositories;

import dev.nateschieber.groovesprings.entities.Playlist;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

  @Query("select pl from Playlist pl join pl.albums al where al.id in :albumIds")
  public List<Playlist> findByAlbumIds(@Param("albumIds")List<Long> albumIds);

  @Query("select pl from Playlist pl join pl.artists ar where ar.id in :artistIds")
  public List<Playlist> findByArtistIds(@Param("artistIds")List<Long> artistIds);
}
