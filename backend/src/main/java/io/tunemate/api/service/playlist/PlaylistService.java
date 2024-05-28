package io.tunemate.api.service.playlist;

import io.tunemate.api.model.Playlist;

public interface PlaylistService {
    boolean existsBySpotifyId(String playlistId);

    Playlist findBySpotifyId(String playlistId);

    Playlist createPlaylist(Playlist playlist);
}
