package io.tunemate.api.mapper;

import io.tunemate.api.dto.ArtistDto;
import io.tunemate.api.dto.UserDto;
import io.tunemate.api.model.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {
    public static User mapFromUserDto(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .photoUrl(userDto.getPhotoUrl())
                .build();
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
                .favouriteArtists(artistDtos)
                .build();
    }
}
