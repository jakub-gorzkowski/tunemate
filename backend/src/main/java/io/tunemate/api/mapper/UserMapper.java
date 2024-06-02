package io.tunemate.api.mapper;

import io.tunemate.api.dto.*;
import io.tunemate.api.model.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {
    public static User mapFromUserDto(UserDto userDto) {
        User user = User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .photoUrl(userDto.getPhotoUrl())
                .password(userDto.getPassword())
                .spotifyId(userDto.getSpotifyId())
                .build();

        user.setRealUsername(userDto.getUsername());

        return user;
    }

    public static UserDto mapToUserDto(User user) {
        Set<PlaylistDto> playlists = new HashSet<>();
        if (user.getFavouriteArtists() != null) {
            playlists = user.getPlaylists().stream()
                    .map(PlaylistMapper::mapToPlaylistDto)
                    .collect(Collectors.toSet());
        }
        Set<ArtistDto> artists = new HashSet<>();
        if (user.getFavouriteArtists() != null) {
            artists = user.getFavouriteArtists().stream()
                    .map(ArtistMapper::mapToArtistDto)
                    .collect(Collectors.toSet());
        }
        Set<ReleaseDto> releases = new HashSet<>();
        if (user.getFavouriteArtists() != null) {
            releases = user.getFavouriteReleases().stream()
                    .map(ReleaseMapper::mapToReleaseDto)
                    .collect(Collectors.toSet());
        }
        Set<PlaylistDto> favouritePlaylists = new HashSet<>();
        if (user.getFavouriteArtists() != null) {
            favouritePlaylists = user.getPlaylists().stream()
                    .map(PlaylistMapper::mapToPlaylistDto)
                    .collect(Collectors.toSet());
        }
        Set<GenreDto> genres = new HashSet<>();
        if (user.getFavouriteArtists() != null) {
            genres = user.getGenres().stream()
                    .map(GenreMapper::mapToGenreDto)
                    .collect(Collectors.toSet());
        }

        return UserDto.builder()
                .id(user.getId())
                .username(user.getRealUsername())
                .email(user.getEmail())
                .photoUrl(user.getPhotoUrl())
                .spotifyId(user.getSpotifyId())
                .favouriteArtists(artists)
                .favouriteReleases(releases)
                .favouritePlaylists(favouritePlaylists)
                .favouriteGenres(genres)
                .build();
    }
}
