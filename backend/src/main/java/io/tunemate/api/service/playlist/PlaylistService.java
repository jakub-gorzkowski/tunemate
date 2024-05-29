package io.tunemate.api.service.playlist;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.tunemate.api.model.Playlist;

public interface PlaylistService {
    boolean existsBySpotifyId(String playlistId);
    Playlist findBySpotifyId(String playlistId);
    Playlist createPlaylist(Playlist playlist);
    Playlist retrievePlaylist(String playlistId) throws JsonProcessingException;
}
