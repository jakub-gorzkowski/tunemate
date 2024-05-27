package io.tunemate.api.service.spotify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tunemate.api.model.Artist;
import io.tunemate.api.model.Genre;
import io.tunemate.api.model.Release;
import io.tunemate.api.model.Track;
import io.tunemate.api.service.artist.ArtistService;
import io.tunemate.api.service.genre.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Autowired
    public SpotifyServiceImplementation(ArtistService artistService, GenreService genreService) {
        this.artistService = artistService;
        this.genreService = genreService;
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
            if (!genreService.exists(genreName)) {
                Genre artistGenre = new Genre();
                artistGenre.setGenre(genreName);

                Set<Artist> genreArtists = new HashSet<>();
                if (artistGenre.getArtists() != null) {
                    genreArtists = artistGenre.getArtists();
                } else {
                    genreArtists.add(artist);
                }
                artistGenre.setArtists(genreArtists);

                genreService.createGenre(artistGenre);
                genres.add(artistGenre);
            }
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
            topTracks.add(track);
        }

        return topTracks;
    }

//    @Override
//    public Set<Genre> retrieveGenres(String artistId) throws JsonProcessingException {
//        RestTemplate restTemplate = new RestTemplate();
//
//        String url = "https://api.spotify.com/v1/artists/" + artistId;
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + this.getAccessToken());
//        headers.set("Accept", "application/json");
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode rootNode = mapper.readTree(response.getBody());
//        JsonNode genresNode = rootNode.path("genres");
//
//        Set<Genre> genres = new HashSet<>();
//
//        for (JsonNode genre : genresNode) {
//
//        }
//
//        return Genre.builder()
//                .spotifyId(spotifyId)
//                .name(rootNode.path("name").asText())
//                .followerCount(rootNode.path("followers").path("total").asLong())
//                .photoUrl(rootNode.path("images").get(0).path("url").asText())
//                .tracks(null)
//                .build();
//    }
}
