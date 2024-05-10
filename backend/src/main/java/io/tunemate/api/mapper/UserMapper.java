package io.tunemate.api.mapper;

import io.tunemate.api.dto.UserDto;
import io.tunemate.api.model.User;

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
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .photoUrl(user.getPhotoUrl())
                .build();
    }
}
