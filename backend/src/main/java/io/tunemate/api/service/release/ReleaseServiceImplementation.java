package io.tunemate.api.service.release;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tunemate.api.model.Artist;
import io.tunemate.api.model.Release;
import io.tunemate.api.model.Track;
import io.tunemate.api.repository.ReleaseRepository;
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

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Service
public class ReleaseServiceImplementation implements ReleaseService {
    private final SpotifyService spotifyService;
    private final ArtistService artistService;
    private final TrackService trackService;
    private final ReleaseRepository releaseRepository;

    @Autowired
    public ReleaseServiceImplementation(
            SpotifyService spotifyService,
            ArtistService artistService,
            TrackService trackService,
            ReleaseRepository releaseRepository
    ) {
        this.spotifyService = spotifyService;
        this.artistService = artistService;
        this.trackService = trackService;
        this.releaseRepository = releaseRepository;
    }

    @Override
    public boolean existsBySpotifyId(String spotifyId) {
        return releaseRepository.existsById(spotifyId);
    }

    @Override
    public Release findBySpotifyId(String spotifyId) {
        return releaseRepository.findById(spotifyId).get();
    }

    @Override
    public Release createRelease(Release release) {
        return releaseRepository.save(release);
    }

    @Override
    public Release retrieveRelease(String releaseId) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.spotify.com/v1/albums/" + releaseId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + spotifyService.getAccessToken());
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
                    Artist artist = artistService.retrieveArtist(spotifyId);
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
}
