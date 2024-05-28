package io.tunemate.api.service.track;

import io.tunemate.api.model.Track;

public interface TrackService {
    boolean existsBySpotifyId(String spotifyId);
    boolean isTopTrack(String spotifyId);
    Track createTrack(Track track);
}
