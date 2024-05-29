package dev.nateschieber.groovesprings.repositories;

import dev.nateschieber.groovesprings.entities.Album;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlbumRepository extends JpaRepository<Album, Long> {
  @Query(
      "SELECT album FROM Album album JOIN album.artists artists WHERE artists.id in :artistIds "
  )
  public List<Album> findByArtistIds(@Param("artistIds") List<Long> artistIds);
}
