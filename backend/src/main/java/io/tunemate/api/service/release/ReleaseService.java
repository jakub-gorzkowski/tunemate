package io.tunemate.api.service.release;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.tunemate.api.model.Release;

public interface ReleaseService {
    boolean existsBySpotifyId(String spotifyId);
    Release findBySpotifyId(String spotifyId);
    Release createRelease(Release release);
    Release retrieveRelease(String releaseId) throws JsonProcessingException;
}
