package io.tunemate.api.service.artist;

import io.tunemate.api.model.Artist;
import io.tunemate.api.model.Release;
import io.tunemate.api.model.Track;
import io.tunemate.api.repository.ArtistRepository;
import io.tunemate.api.repository.ReleaseRepository;
import io.tunemate.api.repository.TrackRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArtistServiceImplementation implements ArtistService {
    private final ArtistRepository artistRepository;
    private final ReleaseRepository releaseRepository;
    private final TrackRepository trackRepository;

    @Autowired
    public ArtistServiceImplementation(
            ArtistRepository artistRepository,
            ReleaseRepository releaseRepository,
            TrackRepository trackRepository
    ) {
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
        return artistRepository.findById(spotifyId).get();
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
        currentReleases.removeAll(managedReleases);

        currentReleases.addAll(managedReleases);
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
}
