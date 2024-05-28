package io.tunemate.api.service.playlist;

import io.tunemate.api.model.Playlist;
import io.tunemate.api.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaylistServiceImplementation implements PlaylistService {
    private final PlaylistRepository playlistRepository;

    @Autowired
    public PlaylistServiceImplementation(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public boolean existsBySpotifyId(String playlistId) {
        return playlistRepository.existsById(playlistId);
    }

    @Override
    public Playlist findBySpotifyId(String playlistId) {
        return playlistRepository.findById(playlistId).get();
    }

    @Override
    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }
}
