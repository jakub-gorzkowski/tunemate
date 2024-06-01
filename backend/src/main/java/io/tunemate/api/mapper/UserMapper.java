package io.tunemate.api.mapper;

import io.tunemate.api.dto.ArtistDto;
import io.tunemate.api.dto.UserDto;
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
        Set<ArtistDto> artistDtos = new HashSet<>();
        if (user.getFavouriteArtists() != null) {
            artistDtos = user.getFavouriteArtists().stream()
                    .map(ArtistMapper::mapToArtistDto)
                    .collect(Collectors.toSet());
        }
        return UserDto.builder()
                .id(user.getId())
                .username(user.getRealUsername())
                .email(user.getEmail())
                .photoUrl(user.getPhotoUrl())
                .spotifyId(user.getSpotifyId())
                .favouriteArtists(artistDtos)
                .build();
    }
}
