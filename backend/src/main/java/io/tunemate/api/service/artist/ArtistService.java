package io.tunemate.api.service.artist;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.tunemate.api.model.Artist;
import io.tunemate.api.model.Release;
import io.tunemate.api.model.Track;

import java.util.Set;

public interface ArtistService {
    boolean existsBySpotifyId(String spotifyId);
    Artist createArtist(Artist artist);
    Artist findById(String spotifyId);
    Artist updateReleases(Artist artist, Set<Release> releases);
    Artist updateTopTracks(Artist artist, Set<Track> tracks);
    Artist retrieveArtist(String artistId) throws JsonProcessingException;
    Set<Track> retrieveArtistTopTracks(String artistId) throws JsonProcessingException;
    Set<Release> retrieveArtistReleases(String artistId) throws JsonProcessingException;
}
