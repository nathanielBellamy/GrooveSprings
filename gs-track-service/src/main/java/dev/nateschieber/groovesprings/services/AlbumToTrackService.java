package dev.nateschieber.groovesprings.services;

import dev.nateschieber.groovesprings.entities.Album;
import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.joins.AlbumToTrack;
import dev.nateschieber.groovesprings.repositories.AlbumRepository;
import dev.nateschieber.groovesprings.repositories.AlbumToTrackRepository;
import dev.nateschieber.groovesprings.repositories.TrackRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumToTrackService {
  private final AlbumToTrackRepository repository;
  private final AlbumRepository albumRepository;
  private final TrackRepository trackRepository;

  @Autowired
  public AlbumToTrackService(
      AlbumToTrackRepository albumToTrackRepository,
      AlbumRepository albumRepository,
      TrackRepository trackRepository) {
    this.repository = albumToTrackRepository;
    this.albumRepository = albumRepository;
    this.trackRepository = trackRepository;
  }

  public void joinTracksToAlbum(Long albumId, List<Track> tracks) {
    Optional<Album> album = albumRepository.findById(albumId);
    if (album.isPresent()) {
      List<AlbumToTrack> atts = tracks
          .stream()
          .map(t -> new AlbumToTrack(null, albumId, t.getId(), false))
          .collect(Collectors.toList());

      this.repository.saveAllAndFlush(atts);
    }
  }

  public List<Track> findTracksByAlbumId(Long albumId) {
    List<Track> tracks = new ArrayList<>();
    Collection<Long> trackIds = this.repository.findTrackIdsByAlbumId(albumId);
    Iterator<Long> iter = trackIds.iterator();
    while (iter.hasNext()) {
      Optional<Track> optTrack = this.trackRepository.findById(iter.next());
      if (optTrack.isPresent()) {
        tracks.add(optTrack.get());
      }
    }

    Collections.sort(tracks, Comparator.comparing(Track::getId));

    return tracks;
  }
}
