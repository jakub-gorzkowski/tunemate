package io.tunemate.api.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tunemate.api.dto.UserDto;
import io.tunemate.api.model.Artist;
import io.tunemate.api.model.Playlist;
import io.tunemate.api.model.User;
import io.tunemate.api.repository.ArtistRepository;
import io.tunemate.api.repository.UserRepository;
import io.tunemate.api.service.spotify.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static io.tunemate.api.mapper.UserMapper.mapToUserDto;

@Service
public class UserServiceImplementation implements UserService {
    private final SpotifyService spotifyService;
    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;

    @Autowired
    public UserServiceImplementation(
            SpotifyService spotifyService,
            UserRepository userRepository,
            ArtistRepository artistRepository
    ) {
        this.spotifyService = spotifyService;
        this.userRepository = userRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public Boolean exists(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDto findUserById(Long userId) {
        User user = userRepository.findById(userId).get();
        return mapToUserDto(user);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).get();
        return mapToUserDto(user);
    }

    @Override
    public User updateUser(Long userId, User user) {
        user.setId(userId);
        return userRepository.findById(userId).map(existingUser -> {
            Optional.ofNullable(user.getPhotoUrl()).ifPresent(existingUser::setPhotoUrl);
            Optional.ofNullable(user.getUsername()).ifPresent(existingUser::setUsername);
            Optional.ofNullable(user.getEmail()).ifPresent(existingUser::setEmail);
            Optional.ofNullable(user.getPassword()).ifPresent(existingUser::setPassword);
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User does not exist"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Long getUserIdByEmail(String email) {
        User user = userRepository.findByUsername(email).get();
        return user.getId();
    }

    @Override
    public User followArtist(Long userId, String artistId) {
        User user = userRepository.findById(userId).get();
        Artist artist = artistRepository.findById(artistId).get();

        Set<Artist> favouriteArtists = user.getFavouriteArtists();
        Set<User> fans = artist.getUsers();

        favouriteArtists.add(artist);
        fans.add(user);

        user.setFavouriteArtists(favouriteArtists);
        artist.setUsers(fans);

        return userRepository.save(user);
    }

    @Override
    public Set<Playlist> retrieveUserPlaylists(String userId) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.spotify.com/v1/users/" + userId + "/playlists";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + spotifyService.getAccessToken());
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.getBody());
        JsonNode itemsNode = rootNode.path("items");

        Set<Playlist> playlists = new HashSet<>();

        for (JsonNode itemNode : itemsNode) {
            Playlist playlist = Playlist.builder()
                    .spotifyId(itemNode.path("id").asText())
                    .title(itemNode.path("name").asText())
                    .photoUrl(itemNode.path("images").get(0).path("url").asText())
                    .build();
            playlists.add(playlist);
        }

        return playlists;
    }
}
