package io.tunemate.api.service.playlist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tunemate.api.model.Artist;
import io.tunemate.api.model.Playlist;
import io.tunemate.api.model.Track;
import io.tunemate.api.repository.PlaylistRepository;
import io.tunemate.api.service.artist.ArtistService;
import io.tunemate.api.service.spotify.SpotifyService;
import io.tunemate.api.service.track.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PlaylistServiceImplementation implements PlaylistService {
    private final SpotifyService spotifyService;
    private final ArtistService artistService;
    private final TrackService trackService;
    private final PlaylistRepository playlistRepository;

    @Autowired
    public PlaylistServiceImplementation(
            SpotifyService spotifyService,
            ArtistService artistService,
            TrackService trackService,
            PlaylistRepository playlistRepository
    ) {
        this.spotifyService = spotifyService;
        this.artistService = artistService;
        this.trackService = trackService;
        this.playlistRepository = playlistRepository;
    }

    @Override
    public boolean existsBySpotifyId(String playlistId) {
        return playlistRepository.existsById(playlistId);
    }

    @Override
    public Playlist findBySpotifyId(String playlistId) {
        return playlistRepository.findById(playlistId).get();
    }

    @Override
    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    @Override
    public Playlist retrievePlaylist(String playlistId) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.spotify.com/v1/playlists/" + playlistId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + spotifyService.getAccessToken());
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.getBody());
        JsonNode itemsNode = rootNode.path("tracks").path("items");

        List<Track> tracks = new ArrayList<>();

        for (JsonNode itemNode : itemsNode) {
            Set<Artist> artists = new HashSet<>();
            JsonNode artistsNode = itemNode.path("track").path("artists");

            for (JsonNode artistNode : artistsNode) {
                String spotifyId = artistNode.path("id").asText();

                if (artistService.existsBySpotifyId(spotifyId)) {
                    artists.add(artistService.findById(spotifyId));
                } else {
                    Artist artist = artistService.retrieveArtist(spotifyId);
                    artistService.createArtist(artist);
                }
            }

            Track track = Track.builder()
                    .spotifyId(itemNode.path("track").path("id").asText())
                    .title(itemNode.path("track").path("name").asText())
                    .duration(itemNode.path("track").path("duration_ms").asLong())
                    .trackNumber(itemNode.path("track").path("track_number").asLong())
                    .artists(artists)
                    .build();

            if (trackService.isTopTrack(track.getSpotifyId())) {
                track.setIsTopTrack(true);
            }

            tracks.add(track);
        }

        return Playlist.builder()
                .spotifyId(rootNode.path("id").asText())
                .title(rootNode.path("name").asText())
                .photoUrl(rootNode.path("images").get(0).path("url").asText())
                .tracks(tracks)
                .build();
    }
}
