package io.tunemate.api.service.artist;

import io.tunemate.api.model.Artist;
import io.tunemate.api.model.Release;

import java.util.Set;

public interface ArtistService {
    boolean existsBySpotifyId(String spotifyId);
    Artist createArtist(Artist artist);
    Artist findById(String spotifyId);
    Artist updateReleases(Artist artist, Set<Release> releases);
}
