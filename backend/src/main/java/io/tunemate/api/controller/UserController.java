package io.tunemate.api.controller;

import io.tunemate.api.dto.GenreDto;
import io.tunemate.api.dto.PlaylistDto;
import io.tunemate.api.dto.ReviewDto;
import io.tunemate.api.dto.UserDto;
import io.tunemate.api.mapper.GenreMapper;
import io.tunemate.api.mapper.PlaylistMapper;
import io.tunemate.api.mapper.ReviewMapper;
import io.tunemate.api.model.Genre;
import io.tunemate.api.model.Playlist;
import io.tunemate.api.model.Review;
import io.tunemate.api.model.User;
import io.tunemate.api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

import static io.tunemate.api.mapper.UserMapper.*;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/get")
    public ResponseEntity<UserDto> readUser(@AuthenticationPrincipal User user) {
        User retrievedUser = userService.findUserById(user.getId());
        return new ResponseEntity<>(mapToUserDto(retrievedUser), HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDto> readUserById(@PathVariable Long userId) {
        User user = userService.findUserById(userId);
        return new ResponseEntity<>(mapToUserDto(user), HttpStatus.OK);
    }

    @PatchMapping(path = "/update")
    public ResponseEntity<UserDto> updateUser(@AuthenticationPrincipal User user, @RequestBody UserDto userBody) {
        if (!userService.exists(user.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(user.getId());
        User updatedUser = userService.updateUser(user.getId(), mapFromUserDto(userBody));
        return new ResponseEntity<>(mapToUserDto(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity deleteUser(@AuthenticationPrincipal User user) {
        userService.deleteUser(user.getId());
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

    @GetMapping(path = "/favourite-playlists")
    public ResponseEntity<Set<PlaylistDto>> getFavouritePlaylists(@AuthenticationPrincipal User user) {
        Set<Playlist> playlists = userService.getFavouritePlaylists(user.getId());

        return new ResponseEntity<>(playlists.stream()
                .map(PlaylistMapper::mapToLabel)
                .collect(Collectors.toSet()), HttpStatus.OK);
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

    @GetMapping(path = "/genres")
    public ResponseEntity<Set<GenreDto>> getGenres(@AuthenticationPrincipal User user) {
        Set<Genre> genres = userService.getFavouriteGenres(user.getId());

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

    @GetMapping(path = "/reviews/{userId}")
    public ResponseEntity<Set<ReviewDto>> getUserReviews(@PathVariable Long userId) {
        Set<Review> reviews = userService.getReviews(userId);

        return new ResponseEntity<>(reviews.stream()
                .map(ReviewMapper::mapToReviewDto)
                .collect(Collectors.toSet()), HttpStatus.OK);
    }
}
