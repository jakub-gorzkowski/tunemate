package io.tunemate.api.service.user;

import io.tunemate.api.dto.UserDto;
import io.tunemate.api.model.User;

public interface UserService {
    Boolean exists(Long userId);
    User createUser(User user);
    UserDto findUserById(Long userId);
    User updateUser(Long userId, User user);
    void deleteUser(Long userId);
}
