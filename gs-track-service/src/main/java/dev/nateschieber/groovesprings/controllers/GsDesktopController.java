package dev.nateschieber.groovesprings.controllers;
import dev.nateschieber.groovesprings.services.entities.AlbumService;
import dev.nateschieber.groovesprings.services.entities.ArtistService;
import dev.nateschieber.groovesprings.services.entities.TrackService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/desktop")
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
    public ResponseEntity scanMusicLibrary() {
        // TODO
        return ResponseEntity.ok().build();
    }
}
