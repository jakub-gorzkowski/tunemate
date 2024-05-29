package io.tunemate.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.tunemate.api.dto.PlaylistDto;
import io.tunemate.api.mapper.PlaylistMapper;
import io.tunemate.api.model.Playlist;
import io.tunemate.api.service.playlist.PlaylistService;
import io.tunemate.api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

import static io.tunemate.api.mapper.PlaylistMapper.mapToPlaylistDto;

@RestController
@RequestMapping(path = "/api/playlists")
public class PlaylistController {
    private final UserService userService;
    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(UserService userService, PlaylistService playlistService) {
        this.userService= userService;
        this.playlistService = playlistService;
    }

    @GetMapping(path = "/get/{playlistId}")
    public ResponseEntity<PlaylistDto> getPlaylist(@PathVariable String playlistId) throws JsonProcessingException {
        Playlist playlist;

        if (!playlistService.existsBySpotifyId(playlistId)) {
            playlist = playlistService.retrievePlaylist(playlistId);
            playlistService.createPlaylist(playlist);
        } else {
            playlist = playlistService.findBySpotifyId(playlistId);
            if (playlist.getTracks().isEmpty()) {
                playlist = playlistService.retrievePlaylist(playlistId);
                playlistService.createPlaylist(playlist);
            }
        }

        playlist = playlistService.findBySpotifyId(playlistId);

        return new ResponseEntity<>(mapToPlaylistDto(playlist), HttpStatus.OK);
    }

    @GetMapping(path = "/get/{userId}/playlists")
    public ResponseEntity<Set<PlaylistDto>> getUserPlaylists(@PathVariable String userId) throws JsonProcessingException {
        Set<Playlist> playlists = userService.retrieveUserPlaylists(userId);
        return new ResponseEntity<>(playlists.stream()
                .map(PlaylistMapper::mapToPlaylistDto)
                .collect(Collectors.toSet()), HttpStatus.OK);
    }
}
