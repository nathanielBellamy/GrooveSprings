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

  List<Track> findByReleaseDateBetween(LocalDate start, LocalDate end);
  List<Track> findByAudioCodec(AudioCodec audioCodec);
  List<Track> findByDurationBetween(Long min, Long max);
}
