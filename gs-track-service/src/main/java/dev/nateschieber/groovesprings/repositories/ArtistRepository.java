package dev.nateschieber.groovesprings.repositories;

import dev.nateschieber.groovesprings.entities.Artist;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

  @Query("select artist from Artist artist join artist.albums album where album.id in :albumIds")
  public List<Artist> findByAlbumIds(@Param("albumIds")List<Long> albumIds);
}
