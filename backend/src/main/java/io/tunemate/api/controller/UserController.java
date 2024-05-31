package io.tunemate.api.controller;

import io.tunemate.api.dto.GenreDto;
import io.tunemate.api.dto.PlaylistDto;
import io.tunemate.api.dto.UserDto;
import io.tunemate.api.mapper.GenreMapper;
import io.tunemate.api.mapper.PlaylistMapper;
import io.tunemate.api.model.Genre;
import io.tunemate.api.model.Playlist;
import io.tunemate.api.model.User;
import io.tunemate.api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

import static io.tunemate.api.mapper.UserMapper.mapToUserDto;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/get")
    public ResponseEntity<UserDto> readUserById(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(mapToUserDto(user), HttpStatus.OK);
    }

    @PatchMapping(path = "/update/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId, @RequestBody User user) {
        if (!userService.exists(userId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(userId);
        User updatedUser = userService.updateUser(userId, user);
        return new ResponseEntity<>(mapToUserDto(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/follow/{artistId}")
    public ResponseEntity<UserDto> followArtist(
            @AuthenticationPrincipal User user,
            @PathVariable("artistId") String artistId
    ) {
        User retrievedUser = userService.followArtist(user.getId(), artistId);
        return new ResponseEntity<>(mapToUserDto(retrievedUser), HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}/favourite-playlists")
    public ResponseEntity<Set<PlaylistDto>> getUserFavouritePlaylists(@PathVariable Long userId) {
        Set<Playlist> playlists = userService.getFavouritePlaylists(userId);

        return new ResponseEntity<>(playlists.stream()
                .map(PlaylistMapper::mapToPlaylistDto)
                .collect(Collectors.toSet()), HttpStatus.OK);
    }

    @PatchMapping(path = "/add-favourite-playlist/{playlistId}")
    public ResponseEntity<Set<PlaylistDto>> addPlaylistsToFavourites(
            @AuthenticationPrincipal User user,
            @PathVariable String playlistId
    ) {
        userService.updateFavouritePlaylists(user.getId(), playlistId);
        Set<Playlist> playlists = userService.getFavouritePlaylists(user.getId());

        return new ResponseEntity<>(playlists.stream()
                .map(PlaylistMapper::mapToPlaylistDto)
                .collect(Collectors.toSet()), HttpStatus.OK);
    }

    @DeleteMapping(path = "/remove-from-favourite-playlists/{playlistId}")
    public ResponseEntity<Set<PlaylistDto>> removePlaylistFromFavourites(
            @AuthenticationPrincipal User user,
            @PathVariable String playlistId
    ) {
        userService.removePlaylistFromFavourites(user.getId(), playlistId);
        Set<Playlist> playlists = userService.getFavouritePlaylists(user.getId());

        return new ResponseEntity<>(playlists.stream()
                .map(PlaylistMapper::mapToPlaylistDto)
                .collect(Collectors.toSet()), HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}/genres")
    public ResponseEntity<Set<GenreDto>> getFavouriteGenres(@PathVariable Long userId) {
        Set<Genre> genres = userService.getFavouriteGenres(userId);

        return new ResponseEntity<>(genres.stream()
                .map(GenreMapper::mapToGenreDto)
                .collect(Collectors.toSet()), HttpStatus.OK);
    }

    @PatchMapping(path = "/add-genre/{genre}")
    public ResponseEntity<Set<GenreDto>> addGenreToFavourites(
            @AuthenticationPrincipal User user,
            @PathVariable String genre
    ) {
        userService.addGenre(user.getId(), genre);
        Set<Genre> genres = userService.getFavouriteGenres(user.getId());

        return new ResponseEntity<>(genres.stream()
                .map(GenreMapper::mapToGenreDto)
                .collect(Collectors.toSet()), HttpStatus.OK);
    }

    @DeleteMapping(path = "/remove-genre/{genre}")
    public ResponseEntity<Set<GenreDto>> removeGenreFromFavourites(
            @AuthenticationPrincipal User user,
            @PathVariable String genre
    ) {
        userService.removeGenre(user.getId(), genre);
        Set<Genre> genres = userService.getFavouriteGenres(user.getId());

        return new ResponseEntity<>(genres.stream()
                .map(GenreMapper::mapToGenreDto)
                .collect(Collectors.toSet()), HttpStatus.OK);
    }
}
