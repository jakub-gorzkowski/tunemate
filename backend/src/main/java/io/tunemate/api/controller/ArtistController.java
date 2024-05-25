package io.tunemate.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.tunemate.api.model.Artist;
import io.tunemate.api.service.artist.ArtistService;
import io.tunemate.api.service.spotify.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {
    private final ArtistService artistService;
    private final SpotifyService spotifyService;

    @Autowired
    public ArtistController(ArtistService artistService, SpotifyService spotifyService) {
        this.artistService = artistService;
        this.spotifyService = spotifyService;
    }

    @GetMapping("/get/{artistId}")
    public ResponseEntity<Artist> getArtist(@PathVariable String artistId) throws JsonProcessingException {
        if (!artistService.existsBySpotifyId(artistId)) {
            Artist artist = spotifyService.retrieveArtist(artistId);
            artistService.createArtist(artist);
            return new ResponseEntity<>(artist, HttpStatus.CREATED);
        } else {
            Artist artist = artistService.findById(artistId);
            return new ResponseEntity<>(artist, HttpStatus.OK);
        }
    }
}