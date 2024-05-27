package io.tunemate.api.service.spotify;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.tunemate.api.model.Artist;
import io.tunemate.api.model.Release;
import io.tunemate.api.model.Track;

import java.text.ParseException;
import java.util.Set;

public interface SpotifyService {
    String getAccessToken();
    Artist retrieveArtist(String artistId) throws JsonProcessingException;
    Set<Release> retrieveArtistReleases(String artistId) throws JsonProcessingException;
    Set<Track> retrieveArtistTopTracks(String artistId) throws JsonProcessingException;
}
