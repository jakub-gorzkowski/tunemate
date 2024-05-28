package io.tunemate.api.service.spotify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tunemate.api.model.*;
import io.tunemate.api.service.artist.ArtistService;
import io.tunemate.api.service.genre.GenreService;
import io.tunemate.api.service.release.ReleaseService;
import io.tunemate.api.service.track.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

@Service
public class SpotifyServiceImplementation implements SpotifyService {

    @Value("${application.spotify.client-id}")
    private String clientId;
    @Value("${application.spotify.client-secret}")
    private String clientSecret;
    private final ArtistService artistService;
    private final GenreService genreService;
    private final TrackService trackService;

    @Autowired
    public SpotifyServiceImplementation(
            ArtistService artistService,
            GenreService genreService,
            TrackService trackService
    ) {
        this.artistService = artistService;
        this.genreService = genreService;
        this.trackService = trackService;
    }

    @Override
    public String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://accounts.spotify.com/api/token";
        String authString = clientId + ":" + clientSecret;
        String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuthString);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=client_credentials";
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        Map<String, Object> responseBody = response.getBody();
        return responseBody.get("access_token").toString();
    }

    @Override
    public Artist retrieveArtist(String artistId) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.spotify.com/v1/artists/" + artistId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.getAccessToken());
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.getBody());
        JsonNode genresNode = rootNode.path("genres");

        String spotifyId = rootNode.path("id").asText();

        Artist artist = Artist.builder()
                .spotifyId(spotifyId)
                .name(rootNode.path("name").asText())
                .followerCount(rootNode.path("followers").path("total").asLong())
                .photoUrl(rootNode.path("images").get(0).path("url").asText())
                .tracks(null)
                .genres(null)
                .build();

        Set<Genre> genres = new HashSet<>();

        for (JsonNode genre : genresNode) {
            String genreName = genre.asText();

            Genre artistGenre = genreService.findByName(genreName);
            if (artistGenre == null) {
                artistGenre = Genre.builder()
                        .genre(genreName)
                        .build();
            }

            Set<Artist> genreArtists = artistGenre.getArtists();
            if (genreArtists == null) {
                genreArtists = new HashSet<>();
            }
            genreArtists.add(artist);
            artistGenre.setArtists(genreArtists);

            genres.add(artistGenre);
        }

        artist.setGenres(genres);

        return artist;
    }

    @Override
    public Set<Release> retrieveArtistReleases(String artistId) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.spotify.com/v1/artists/" + artistId + "/albums";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.getAccessToken());
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.getBody());
        JsonNode itemsNode = rootNode.path("items");

        Set<Release> releases = new TreeSet<>(Comparator.comparing(Release::getReleaseDate).reversed());

        for (JsonNode itemNode : itemsNode) {
            Set<Artist> artists = new HashSet<>();
            JsonNode artistsNode = itemNode.path("artists");

            for (JsonNode artistNode : artistsNode) {
                String spotifyId = artistNode.path("id").asText();

                if (artistService.existsBySpotifyId(spotifyId)) {
                    artists.add(artistService.findById(spotifyId));
                } else {
                    Artist artist = this.retrieveArtist(spotifyId);
                    artistService.createArtist(artist);
                }
            }

            String date = itemNode.path("release_date").asText();
            LocalDate releaseDate = LocalDate.parse(date);

            Release release = Release.builder()
                    .spotifyId(itemNode.path("id").asText())
                    .title(itemNode.path("name").asText())
                    .photoUrl(itemNode.path("images").get(0).path("url").asText())
                    .totalTracks(itemNode.path("total_tracks").asLong())
                    .releaseDate(releaseDate)
                    .artists(artists)
                    .build();
            releases.add(release);
        }

        return releases;
    }

    @Override
    public Set<Track> retrieveArtistTopTracks(String artistId) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.spotify.com/v1/artists/" + artistId + "/top-tracks";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.getAccessToken());
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.getBody());
        JsonNode tracksNode = rootNode.path("tracks");

        Set<Track> topTracks = new HashSet<>();

        for (JsonNode itemNode : tracksNode) {
            Set<Artist> artists = new HashSet<>();
            JsonNode artistsNode = itemNode.path("artists");

            for (JsonNode artistNode : artistsNode) {
                String spotifyId = artistNode.path("id").asText();

                if (artistService.existsBySpotifyId(spotifyId)) {
                    artists.add(artistService.findById(spotifyId));
                } else {
                    Artist artist = this.retrieveArtist(spotifyId);
                    artistService.createArtist(artist);
                }
            }

            Track track = Track.builder()
                    .spotifyId(itemNode.path("id").asText())
                    .title(itemNode.path("name").asText())
                    .duration(itemNode.path("duration_ms").asLong())
                    .artists(artists)
                    .isTopTrack(true)
                    .build();

            if (trackService.existsBySpotifyId(track.getSpotifyId())) {
                trackService.createTrack(track);
            }

            topTracks.add(track);
        }

        return topTracks;
    }

    @Override
    public Release retrieveRelease(String releaseId) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.spotify.com/v1/albums/" + releaseId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.getAccessToken());
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.getBody());
        JsonNode tracksNode = rootNode.path("tracks").path("items");

        String date = rootNode.path("release_date").asText();
        LocalDate releaseDate = LocalDate.parse(date);

        Set<Track> tracks = new TreeSet<>(Comparator.comparing(Track::getTrackNumber));

        for (JsonNode trackNode : tracksNode) {
            Set<Artist> artists = new HashSet<>();
            JsonNode artistsNode = trackNode.path("artists");

            for (JsonNode artistNode : artistsNode) {
                String spotifyId = artistNode.path("id").asText();

                if (artistService.existsBySpotifyId(spotifyId)) {
                    artists.add(artistService.findById(spotifyId));
                } else {
                    Artist artist = this.retrieveArtist(spotifyId);
                    artistService.createArtist(artist);
                }
            }

            Track track = Track.builder()
                    .spotifyId(trackNode.path("id").asText())
                    .title(trackNode.path("name").asText())
                    .duration(trackNode.path("duration_ms").asLong())
                    .trackNumber(trackNode.path("track_number").asLong())
                    .artists(artists)
                    .build();

            if (trackService.isTopTrack(track.getSpotifyId())) {
                track.setIsTopTrack(true);
            }

            tracks.add(track);
        }

        return Release.builder()
                .spotifyId(rootNode.path("id").asText())
                .title(rootNode.path("name").asText())
                .photoUrl(rootNode.path("images").get(0).path("url").asText())
                .totalTracks(rootNode.path("total_tracks").asLong())
                .releaseDate(releaseDate)
                .tracks(tracks)
                .build();
    }

    @Override
    public Set<Playlist> retrieveUserPlaylists(String userId) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.spotify.com/v1/users/" + userId + "/playlists";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.getAccessToken());
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

    @Override
    public Playlist retrievePlaylist(String playlistId) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.spotify.com/v1/playlists/" + playlistId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.getAccessToken());
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
                    Artist artist = this.retrieveArtist(spotifyId);
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
