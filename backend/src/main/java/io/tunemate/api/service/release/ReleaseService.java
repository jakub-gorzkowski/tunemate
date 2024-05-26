package io.tunemate.api.service.release;

import io.tunemate.api.model.Release;

public interface ReleaseService {
    boolean existsBySpotifyId(String spotifyId);
    Release createRelease(Release release);
}
