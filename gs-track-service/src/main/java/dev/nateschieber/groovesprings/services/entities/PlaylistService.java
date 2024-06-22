package dev.nateschieber.groovesprings.services.entities;

import dev.nateschieber.groovesprings.entities.Album;
import dev.nateschieber.groovesprings.entities.Artist;
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
  private final AlbumService albumService;
  private final ArtistService artistService;

  @Autowired
  public PlaylistService(
          PlaylistRepository playlistRepository,
          TrackService trackService,
          AlbumService albumService,
          ArtistService artistService) {
    this.playlistRepository = playlistRepository;
    this.trackService = trackService;
    this.albumService = albumService;
    this.artistService = artistService;
  }

  public List<Playlist> findAll() {
    return this.playlistRepository.findAll();
  }

  public Optional<Playlist> findById(Long id) {
    return playlistRepository.findById(id);
  }

  public Playlist createFromDto(PlaylistCreateDto dto) {
    Playlist playlist = new Playlist(dto.name(), dto.trackIds());

    List<Track> tracks = trackService.findAllById(dto.trackIds());
    playlist.setTracks(new HashSet<>(tracks));

    Set<Album> albums = tracks
            .stream()
            .map(Track::getAlbum)
            .distinct()
            .collect(Collectors.toSet());
    playlist.setAlbums(albums);

    Set<Artist> artists = tracks
            .stream()
            .flatMap(track -> track.getArtists().stream())
            .distinct()
            .collect(Collectors.toSet());
    playlist.setArtists(artists);

    Playlist playlistSaved = playlistRepository.save(playlist);

    tracks.forEach(t -> {
      t.addPlaylist(playlistSaved);
      trackService.save(t);
    });

    albums.forEach(a -> {
      a.addPlaylist(playlistSaved);
      albumService.save(a);
    });

    artists.forEach(a -> {
      a.addPlaylist(playlistSaved);
      artistService.save(a);
    });

    return playlistSaved;
  }

  public void deleteAll() {
    playlistRepository.deleteAll();
  }
}
