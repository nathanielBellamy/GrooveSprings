package dev.nateschieber.groovesprings.services;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.repositories.TrackRepository;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackEntityDto;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackService {
  private final TrackRepository trackRepository;
  private final AlbumToTrackService albumToTrackService;

  @Autowired
  public TrackService(
      TrackRepository trackRepository,
      AlbumToTrackService albumToTrackService) {
    this.trackRepository = trackRepository;
    this.albumToTrackService = albumToTrackService;
  }

  public List<Track> findAll() {
    return this.trackRepository.findAll();
  }

  public Optional<Track> findById(Long id) {
    return this.trackRepository.findById(id);
  }

  public void deleteById(Long id) {
    this.trackRepository.deleteById(id);
  }

  public Track update(Long id, TrackEntityDto dto) {
    Track updatedTrack = new Track(id, dto);
    return this.trackRepository.save(updatedTrack);
  }

  public Track save(Track track, long artistId, long albumId) {
    Track savedTrack = this.trackRepository.save(track);

    this.albumToTrackService.joinTracksToAlbum(albumId, List.of(savedTrack));

    return savedTrack;
  }
}
