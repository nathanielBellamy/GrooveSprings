package dev.nateschieber.groovesprings.services.entities;

import dev.nateschieber.groovesprings.entities.Album;
import dev.nateschieber.groovesprings.entities.Artist;
import dev.nateschieber.groovesprings.entities.Track;
import dev.nateschieber.groovesprings.enums.AudioCodec;
import dev.nateschieber.groovesprings.enums.Genre;
import dev.nateschieber.groovesprings.repositories.TrackRepository;
import dev.nateschieber.groovesprings.rest.GsDesktopTrackCreateDto;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackCreateDto;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackDto;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackEntityDto;
import dev.nateschieber.groovesprings.rest.dtos.track.TrackUpdateDto;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.Integer.parseInt;

@Service
public class TrackService implements ITrackService<Track, TrackUpdateDto, TrackCreateDto> {
  private final AlbumService albumService;
  private final ArtistService artistService;
  private final TrackRepository trackRepository;

  @Autowired
  public TrackService(
      AlbumService albumService,
      ArtistService artistService,
      TrackRepository trackRepository) {
    this.albumService = albumService;
    this.artistService = artistService;
    this.trackRepository = trackRepository;
  }

  @Override
  public List<Track> findAll() {
    return this.trackRepository.findAll();
  }

  public List<Track> findByArtistIds(List<Long> artistIds) {
    return this.trackRepository.findByArtistIds(artistIds);
  }

  public List<Track> findByAlbumIds(List<Long> albumIds) {
    return trackRepository.findByAlbumIds(albumIds);
  }

  @Override
  public Optional<Track> findById(Long id) {
    return this.trackRepository.findById(id);
  }

  @Override
  public void deleteById(Long id) {
    this.trackRepository.deleteById(id);
  }

  @Override
  public Track update(TrackUpdateDto dto) {
    List<Artist> newArtists = artistService.findAllById(dto.artistIds());
    Album newAlbum = albumService.findById(dto.albumId()).orElse(null);
    Track updatedTrack = new Track(dto, newArtists, newAlbum);

    updatedTrack.getArtists().stream().forEach(a -> {
      a.addTrack(updatedTrack);
      artistService.save(a);
    });

    Album album = updatedTrack.getAlbum();
    if (album != null) {
      album.addTrack(updatedTrack);
      albumService.save(album);
    }

      return this.trackRepository.save(updatedTrack);
  }

  @Override
  public Track createFromDto(TrackCreateDto dto) {
    Optional<Album> album = albumService.findById(dto.albumId());
    List<Artist> artists = artistService.findAllById(dto.artistIds());
    Track track = new Track(
        null,
        artists,
        album.orElse(null),
        dto.title(),
        dto.trackNumber(),
        dto.duration(),
        dto.audioCodec(),
        dto.genres(),
        dto.releaseDate(),
            null,
            null,
            null,
            null,
            null);
    return trackRepository.save(track);
  }

  public Track createFromGsDesktopTrackCreateDto(GsDesktopTrackCreateDto dto) {
    List<Artist> artists = Collections.emptyList(); //artistService.findOrCreateAllByName(dto.artistNames());
    Album album = new Album(); // albumService.findOrCreateByTitleAndArtists(dto.albumTitle());
    Integer releaseYear;
    try {
      releaseYear = parseInt(dto.releaseYear());
    } catch (Exception e) {
      releaseYear = Integer.valueOf(0);
    }
    LocalDate releaseDate = LocalDate.of(releaseYear, 1, 1);
    Track track = new Track(
            null,
            artists,
            album,
            dto.trackTitle(),
            dto.trackNumber(),
            dto.trackLength(),
            dto.audioCodec(),
            Collections.emptyList(),
            releaseDate,
            dto.path().toString(),
            dto.sampleRate(),
            dto.bitRate(),
            dto.isVariableBitRate(),
            dto.isLossless()
    );

    System.out.println(track);

    return new Track();
  }

  @Override
  public Track save(Track track) {
    return trackRepository.save(track);
  }

  @Override
  public List<Track> saveAll(List<Track> tracks) {
    return trackRepository.saveAll(tracks);
  }

  @Override
  public List<Track> findByReleaseYear(int year) {
    return trackRepository.findByReleaseDateBetween(
        LocalDate.of(year, 1, 1),
        LocalDate.of(year, 12, 31)
    );
  }

  @Override
  public List<Track> findByAudioCodec(AudioCodec audioCodec) {
    return trackRepository.findByAudioCodec(audioCodec);
  }

  @Override
  public List<Track> findByDurationBetween(Long min, Long max) {
    if (min == null || min < 0l) {
      min = 0l;
    }

    if (max == null) {
      max = Long.MAX_VALUE;
    }
    return trackRepository.findByDurationBetween(min, max);
  }
}