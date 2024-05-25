package io.tunemate.api.service.artist;

import io.tunemate.api.model.Artist;

public interface ArtistService {
    boolean existsBySpotifyId(String spotifyId);
    Artist createArtist(Artist artist);
    Artist findById(String spotifyId);
}
