package io.tunemate.api.service.artist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tunemate.api.model.Artist;
import io.tunemate.api.model.Genre;
import io.tunemate.api.model.Release;
import io.tunemate.api.model.Track;
import io.tunemate.api.repository.ArtistRepository;
import io.tunemate.api.repository.ReleaseRepository;
import io.tunemate.api.repository.TrackRepository;
import io.tunemate.api.service.genre.GenreService;
import io.tunemate.api.service.spotify.SpotifyService;
import io.tunemate.api.service.track.TrackService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArtistServiceImplementation implements ArtistService {
    private final SpotifyService spotifyService;
    private final GenreService genreService;
    private final TrackService trackService;
    private final ArtistRepository artistRepository;
    private final ReleaseRepository releaseRepository;
    private final TrackRepository trackRepository;

    @Autowired
    public ArtistServiceImplementation(
            SpotifyService spotifyService,
            GenreService genreService,
            TrackService trackService,
            ArtistRepository artistRepository,
            ReleaseRepository releaseRepository,
            TrackRepository trackRepository
    ) {
        this.spotifyService = spotifyService;
        this.genreService = genreService;
        this.trackService = trackService;
        this.artistRepository = artistRepository;
        this.releaseRepository = releaseRepository;
        this.trackRepository = trackRepository;
    }

    @Override
    public boolean existsBySpotifyId(String spotifyId) {
        return artistRepository.existsById(spotifyId);
    }

    @Override
    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    @Override
    public Artist findById(String spotifyId) {
        return artistRepository.findById(spotifyId).orElse(null);
    }

    @Override
    public Artist updateReleases(Artist artist, Set<Release> releases) {
        Set<Release> managedReleases = new HashSet<>();

        for (Release release : releases) {
            Release managedRelease = releaseRepository.findById(release.getSpotifyId())
                    .orElseThrow(EntityNotFoundException::new);
            managedReleases.add(managedRelease);
        }

        Set<Release> currentReleases = artist.getReleases();
        if (currentReleases != null) {
            currentReleases.removeAll(managedReleases);
            currentReleases.addAll(managedReleases);
        }

        artist.setReleases(currentReleases);

        return artistRepository.save(artist);
    }

    @Override
    public Artist updateTopTracks(Artist artist, Set<Track> tracks) {
        Set<Track> managedTracks = new HashSet<>();

        for (Track track : tracks) {
            Track managedRelease = trackRepository.findById(track.getSpotifyId())
                    .orElseThrow(EntityNotFoundException::new);
            managedTracks.add(managedRelease);
        }

        Set<Track> currentTopTracks = artist.getTracks();
        currentTopTracks.removeAll(managedTracks);

        currentTopTracks.addAll(managedTracks);
        artist.setTracks(currentTopTracks);

        return artistRepository.save(artist);
    }

    @Override
    public Set<Artist> getArtistsWithNewReleasesInGenre(Genre genre) {
        Set<Artist> artists = artistRepository.findArtistsByGenres(Set.of(genre));
        return artists.stream()
                .filter(artist -> {
                    Set<Release> releases = artist.getReleases();
                    return releases.stream().anyMatch(release -> LocalDate.now().minusMonths(24).isBefore(release.getReleaseDate()));
                })
                .collect(Collectors.toSet());
    }

    @Override
    public Artist getArtistByName(String name) {
        return artistRepository.findArtistsByName(name);
    }

    @Override
    public Artist retrieveArtist(String artistId) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.spotify.com/v1/artists/" + artistId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + spotifyService.getAccessToken());
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
    public Set<Track> retrieveArtistTopTracks(String artistId) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.spotify.com/v1/artists/" + artistId + "/top-tracks";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + spotifyService.getAccessToken());
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

                if (this.existsBySpotifyId(spotifyId)) {
                    artists.add(this.findById(spotifyId));
                } else {
                    Artist artist = this.retrieveArtist(spotifyId);
                    this.createArtist(artist);
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
    public Set<Release> retrieveArtistReleases(String artistId) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.spotify.com/v1/artists/" + artistId + "/albums";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + spotifyService.getAccessToken());
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

                if (this.existsBySpotifyId(spotifyId)) {
                    artists.add(this.findById(spotifyId));
                } else {
                    Artist artist = this.retrieveArtist(spotifyId);
                    this.createArtist(artist);
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
}
