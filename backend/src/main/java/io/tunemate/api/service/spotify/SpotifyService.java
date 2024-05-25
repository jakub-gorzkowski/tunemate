package io.tunemate.api.service.spotify;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.tunemate.api.model.Artist;

public interface SpotifyService {
    String getAccessToken();
    Artist retrieveArtist(String artistId) throws JsonProcessingException;
}
