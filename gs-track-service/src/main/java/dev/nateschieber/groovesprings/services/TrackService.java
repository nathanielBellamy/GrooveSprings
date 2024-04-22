package dev.nateschieber.groovesprings.services;

import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.repositories.TrackRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackService {
  private final TrackRepository trackRepository;

  @Autowired
  public TrackService(TrackRepository trackRepository) {
    this.trackRepository = trackRepository;
  }

  public List<Track> findAll() {
    return this.trackRepository.findAll();
  }

  public Track save(Track track) {
    Track savedTrack = this.trackRepository.save(track);

    return savedTrack;
  }
}
