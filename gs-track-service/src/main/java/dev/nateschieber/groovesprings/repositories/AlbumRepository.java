package dev.nateschieber.groovesprings.repositories;

import dev.nateschieber.groovesprings.entities.Album;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlbumRepository extends JpaRepository<Album, Long> {
  @Query("select album from Album album join album.artists artist where artist.id in :artistIds ")
  public List<Album> findByArtistIds(@Param("artistIds") List<Long> artistIds);

  @Query("select album from Album album join album.playlists playlist where playlist.id in :playlistIds")
  public List<Album> findByPlaylistIds(@Param("playlistIds") List<Long> playlistIds);

//  @Query(
//          value = "select * from albums as alb " +
//                  "inner join album_to_artist on alb.id = album_to_artist.album_id " +
//                  "inner join artists as art on art.id = album_to_artist.artist_id" +
//                  "where (art.id in :artistIds " +
//                  "and alb.title = :title " +
//                  "and alb.release_date = :releaseDate)",
//          nativeQuery = true
//  )
//  public List<Album> findMatches(
//          @Param("title") String title,
//          @Param("artistIds") List<Long> artistIds,
//          @Param("releaseDate") LocalDate releaseDate
//  );

  // NOTE: loading artists here as we use it to de-dupe during library scan
//  @Query("SELECT album FROM Album album join album.artists artists where album.title = :title")
//  public List<Album> findByTitle(@Param("title") String title);
  public List<Album> findByTitle(String title);
}
