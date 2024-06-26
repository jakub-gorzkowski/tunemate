package io.tunemate.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.tunemate.api.dto.ArtistDto;
import io.tunemate.api.dto.GenreDto;
import io.tunemate.api.dto.ReleaseDto;
import io.tunemate.api.dto.TrackDto;
import io.tunemate.api.mapper.ArtistMapper;
import io.tunemate.api.mapper.GenreMapper;
import io.tunemate.api.mapper.ReleaseMapper;
import io.tunemate.api.mapper.TrackMapper;
import io.tunemate.api.model.*;
import io.tunemate.api.service.artist.ArtistService;
import io.tunemate.api.service.genre.GenreService;
import io.tunemate.api.service.release.ReleaseService;
import io.tunemate.api.service.track.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final ArtistService artistService;
    private final GenreService genreService;
    private final ReleaseService releaseService;
    private final TrackService trackService;

    @Autowired
    public ArtistController(
            ArtistService artistService,
            GenreService genreService,
            ReleaseService releaseService,
            TrackService trackService
    ) {
        this.artistService = artistService;
        this.genreService = genreService;
        this.releaseService = releaseService;
        this.trackService = trackService;
    }

    @GetMapping(path = "/get/{artistId}")
    public ResponseEntity<ArtistDto> getArtist(@PathVariable String artistId) throws JsonProcessingException {
        Artist artist;

        if (!artistService.existsBySpotifyId(artistId)) {
            artist = artistService.retrieveArtist(artistId);
            artistService.createArtist(artist);
        } else {
            artist = artistService.findById(artistId);
        }

        return new ResponseEntity<>(mapToArtistDto(artist), HttpStatus.OK);
    }

    @GetMapping(path = "/get/{artistId}/genres")
    public ResponseEntity<Set<GenreDto>> getGenre(@PathVariable String artistId) throws JsonProcessingException {
        Artist artist;

        if (!artistService.existsBySpotifyId(artistId)) {
            artist = artistService.retrieveArtist(artistId);
            artistService.createArtist(artist);
        } else {
            artist = artistService.findById(artistId);
        }

        Set<Genre> genres = artist.getGenres();

        return new ResponseEntity<>(genres.stream()
                .map(GenreMapper::mapToGenreDto)
                .collect(Collectors.toSet()), HttpStatus.OK);
    }

    @GetMapping(path = "/{genre}/new")
    public ResponseEntity<Set<ArtistDto>> newInGenre(@PathVariable String genre) {
        Genre foundGenre = genreService.findByName(genre);
        Set<Artist> artists = artistService.getArtistsWithNewReleasesInGenre(foundGenre);

        return new ResponseEntity<>(artists.stream()
                .map(ArtistMapper::mapToArtistDto)
                .collect(Collectors.toSet()), HttpStatus.OK);
    }

    @GetMapping(path = "/get/artist-id/{name}")
    public ResponseEntity<String> getArtistIdByName(@PathVariable String name) {
        Artist artist = artistService.getArtistByName(name);
        return new ResponseEntity<>(artist.getSpotifyId(), HttpStatus.OK);
    }

    @GetMapping(path = "/get/{artistId}/albums")
    public ResponseEntity<Set<ReleaseDto>> getArtistReleases(@PathVariable String artistId) throws JsonProcessingException {
        Artist artist;

        if (!artistService.existsBySpotifyId(artistId)) {
            artist = artistService.retrieveArtist(artistId);
            artistService.createArtist(artist);
        } else {
            artist = artistService.findById(artistId);
        }

        Set<Release> artistReleases = artistService.retrieveArtistReleases(artistId);

        for (Release release : artistReleases) {
            if (!releaseService.existsBySpotifyId(release.getSpotifyId())) {
                releaseService.createRelease(release);
            }
        }

        artistService.updateReleases(artist, artistReleases);

        return new ResponseEntity<>(artistReleases.stream()
                .map(ReleaseMapper::mapToReleaseDto)
                .collect(Collectors.toSet()), HttpStatus.OK);
    }

    @GetMapping(path = "/get/{artistId}/top-tracks")
    public ResponseEntity<Set<TrackDto>> getArtistTopTracks(@PathVariable String artistId) throws JsonProcessingException {
        Artist artist;

        if (!artistService.existsBySpotifyId(artistId)) {
            artist = artistService.retrieveArtist(artistId);
            artistService.createArtist(artist);
        } else {
            artist = artistService.findById(artistId);
        }

        Set<Track> topTracks = artistService.retrieveArtistTopTracks(artistId);

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