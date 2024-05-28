package io.tunemate.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.tunemate.api.dto.PlaylistDto;
import io.tunemate.api.model.Playlist;
import io.tunemate.api.service.playlist.PlaylistService;
import io.tunemate.api.service.spotify.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.tunemate.api.mapper.PlaylistMapper.mapToPlaylistDto;

@RestController
@RequestMapping(path = "/api/playlists")
public class PlaylistController {
    private final SpotifyService spotifyService;
    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(SpotifyService spotifyService, PlaylistService playlistService) {
        this.spotifyService = spotifyService;
        this.playlistService = playlistService;
    }

    @GetMapping(path = "/get/{playlistId}")
    public ResponseEntity<PlaylistDto> getPlaylist(@PathVariable String playlistId) throws JsonProcessingException {
        Playlist playlist;

        if (!playlistService.existsBySpotifyId(playlistId)) {
            playlist = spotifyService.retrievePlaylist(playlistId);
            playlistService.createPlaylist(playlist);
        } else {
            playlist = playlistService.findBySpotifyId(playlistId);
            if (playlist.getTracks().isEmpty()) {
                playlist = spotifyService.retrievePlaylist(playlistId);
                playlistService.createPlaylist(playlist);
            }
        }

        playlist = playlistService.findBySpotifyId(playlistId);

        return new ResponseEntity<>(mapToPlaylistDto(playlist), HttpStatus.OK);
    }
}
