package io.tunemate.api.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.tunemate.api.dto.UserDto;
import io.tunemate.api.model.Genre;
import io.tunemate.api.model.Playlist;
import io.tunemate.api.model.Review;
import io.tunemate.api.model.User;

import java.util.Set;

public interface UserService {
    Boolean exists(Long userId);
    User createUser(User user);
    User findUserById(Long userId);
    UserDto findUserByEmail(String email);
    User updateUser(Long userId, User user);
    void deleteUser(Long userId);
    Long getUserIdByEmail(String email);
    User followArtist(Long userId, String artistId);
    User updateFavouritePlaylists(Long userId, String playlistId);
    Set<Playlist> getFavouritePlaylists(Long userId);
    void removePlaylistFromFavourites(Long userId, String playlistId);
    User addGenre(Long userId, String genreName);
    Set<Genre> getFavouriteGenres(Long userId);

    void removeGenre(Long userId, String genre);

    Set<Review> getReviews(Long userId);

    Set<Playlist> retrieveUserPlaylists(String userId) throws JsonProcessingException;
}
