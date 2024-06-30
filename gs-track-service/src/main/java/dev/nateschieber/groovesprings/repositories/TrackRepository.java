package dev.nateschieber.groovesprings.repositories;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrackRepository extends JpaRepository<Track, Long> {

  @Query("select track from Track track join track.artists artist where artist.id in :artistIds")
  List<Track> findByArtistIds(@Param("artistIds") List<Long> artistIds);

  @Query("select track from Track track join track.album album where album.id in :albumIds")
  List<Track> findByAlbumIds(@Param("albumIds") List<Long> albumIds);

  @Query("select track from Track track join track.playlists playlist where playlist.id in :playlistIds")
  List<Track> findByPlaylistIds(@Param("playlistIds") List<Long> playlistIds);

  @Query(
          "select track from Track track " +
                  "where track.title = :title " +
                  "and track.audioCodec = :audioCodec " +
                  "and track.trackNumber = :trackNumber " +
                  "and track.duration = :duration"
  )
  List<Track> findMatch(
          @Param("title") String title,
          @Param("audioCodec") AudioCodec audioCodec,
          @Param("trackNumber") Integer trackNumber,
          @Param("duration") Integer duration
  );

  List<Track> findByReleaseDateBetween(LocalDate start, LocalDate end);
  List<Track> findByAudioCodec(AudioCodec audioCodec);
  List<Track> findByDurationBetween(Long min, Long max);
}
