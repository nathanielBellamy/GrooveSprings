package dev.nateschieber.groovesprings.controllers;
import dev.nateschieber.groovesprings.rest.GsDesktopTrackCreateDto;
import dev.nateschieber.groovesprings.services.entities.AlbumService;
import dev.nateschieber.groovesprings.services.entities.ArtistService;
import dev.nateschieber.groovesprings.services.entities.TrackService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/desktop")
// TODO: @Profile("desktop")
public class GsDesktopController {
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final TrackService trackService;

    @Autowired
    public GsDesktopController(
            AlbumService albumService,
            ArtistService artistService,
            TrackService trackService
    ) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.trackService = trackService;
    }

    @PostMapping("/bulkCreate")
    public ResponseEntity scanMusicLibrary(@Valid @RequestBody List<GsDesktopTrackCreateDto> dtos) {
        dtos
            .stream()
            .forEach(dto -> {
                trackService.createFromGsDesktopTrackCreateDto(dto);
            });
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clearLib")
    public ResponseEntity clearLib() {

        System.out.println("Clearing Library");
        artistService.deleteAll();
        System.out.println("Done Clearing Artists");
        albumService.deleteAll();
        System.out.println("Done Clearing Albums");
        trackService.deleteAll();
        System.out.println("Done Clearing Tracks");
        return ResponseEntity.ok().build();
    }
}
