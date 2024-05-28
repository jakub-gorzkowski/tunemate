package io.tunemate.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.tunemate.api.dto.ArtistDto;
import io.tunemate.api.dto.TrackDto;
import io.tunemate.api.mapper.TrackMapper;
import io.tunemate.api.model.Artist;
import io.tunemate.api.model.Release;
import io.tunemate.api.model.Track;
import io.tunemate.api.service.artist.ArtistService;
import io.tunemate.api.service.release.ReleaseService;
import io.tunemate.api.service.spotify.SpotifyService;
import io.tunemate.api.service.track.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

import static io.tunemate.api.mapper.ArtistMapper.mapToArtistDto;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {
    private final SpotifyService spotifyService;
    private final ArtistService artistService;
    private final ReleaseService releaseService;
    private final TrackService trackService;

    @Autowired
    public ArtistController(
            SpotifyService spotifyService,
            ArtistService artistService,
            ReleaseService releaseService,
            TrackService trackService
    ) {
        this.spotifyService = spotifyService;
        this.artistService = artistService;
        this.releaseService = releaseService;
        this.trackService = trackService;
    }

    @GetMapping(path = "/get/{artistId}")
    public ResponseEntity<Artist> getArtist(@PathVariable String artistId) throws JsonProcessingException {
        Artist artist;

        if (!artistService.existsBySpotifyId(artistId)) {
            artist = spotifyService.retrieveArtist(artistId);
            artistService.createArtist(artist);
        } else {
            artist = artistService.findById(artistId);
        }

        return new ResponseEntity<>(artist, HttpStatus.OK);
    }

    @GetMapping(path = "/get/{artistId}/albums")
    public ResponseEntity<Set<Release>> getArtistReleases(@PathVariable String artistId) throws JsonProcessingException {
        Artist artist;

        if (!artistService.existsBySpotifyId(artistId)) {
            artist = spotifyService.retrieveArtist(artistId);
            artistService.createArtist(artist);
        } else {
            artist = artistService.findById(artistId);
        }

        Set<Release> artistReleases = spotifyService.retrieveArtistReleases(artistId);

        for (Release release : artistReleases) {
            if (!releaseService.existsBySpotifyId(release.getSpotifyId())) {
                releaseService.createRelease(release);
            }
        }

        artistService.updateReleases(artist, artistReleases);

        return new ResponseEntity<>(artistReleases, HttpStatus.OK);
    }

    @GetMapping(path = "/get/{artistId}/top-tracks")
    public ResponseEntity<Set<TrackDto>> getArtistTopTracks(@PathVariable String artistId) throws JsonProcessingException {
        Artist artist;

        if (!artistService.existsBySpotifyId(artistId)) {
            artist = spotifyService.retrieveArtist(artistId);
            artistService.createArtist(artist);
        } else {
            artist = artistService.findById(artistId);
        }

        Set<Track> topTracks = spotifyService.retrieveArtistTopTracks(artistId);

        for (Track track : topTracks) {
            if (!trackService.existsBySpotifyId(track.getSpotifyId())) {
                trackService.createTrack(track);
            }
        }

        artistService.updateTopTracks(artist, topTracks);

        return new ResponseEntity<>(topTracks.stream()
                .map(TrackMapper::mapToTrackDto)
                .collect(Collectors.toSet()), HttpStatus.OK);
    }
}