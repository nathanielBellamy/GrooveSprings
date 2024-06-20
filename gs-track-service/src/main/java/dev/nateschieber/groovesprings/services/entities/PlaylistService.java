package dev.nateschieber.groovesprings.services.entities;

import dev.nateschieber.groovesprings.entities.Playlist;
import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.repositories.PlaylistRepository;
import dev.nateschieber.groovesprings.rest.dtos.playlist.PlaylistCreateDto;
import dev.nateschieber.groovesprings.rest.dtos.playlist.PlaylistDto;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaylistService {
  private final PlaylistRepository playlistRepository;
  private final TrackService trackService;

  @Autowired
  public PlaylistService(PlaylistRepository playlistRepository, TrackService trackService) {
    this.playlistRepository = playlistRepository;
    this.trackService = trackService;
  }

  public List<Playlist> findAll() {
    return this.playlistRepository.findAll();
  }

  public Optional<Playlist> findById(Long id) {
    return playlistRepository.findById(id);
  }

  public Playlist createFromDto(PlaylistCreateDto dto) {
    Playlist playlist = new Playlist(dto.name(), dto.trackIds());

    // TODO: look up albums/artists and establish relationships

    List<Track> tracks = trackService.findAllById(dto.trackIds());
    playlist.setTracks(tracks.stream().collect(Collectors.toSet()));

    Playlist playlistSaved = playlistRepository.save(playlist);

    tracks.forEach(t -> {
      t.addPlaylist(playlist);
      trackService.save(t);
    });

    return playlistSaved;
  }
}
