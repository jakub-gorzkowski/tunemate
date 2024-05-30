package io.tunemate.api.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.tunemate.api.dto.UserDto;
import io.tunemate.api.model.Playlist;
import io.tunemate.api.model.User;

import java.util.Set;

public interface UserService {
    Boolean exists(Long userId);
    User createUser(User user);
    UserDto findUserById(Long userId);
    UserDto findUserByEmail(String email);
    User updateUser(Long userId, User user);
    void deleteUser(Long userId);
    Long getUserIdByEmail(String email);
    User followArtist(Long userId, String artistId);
    Set<Playlist> retrieveUserPlaylists(String userId) throws JsonProcessingException;
}
