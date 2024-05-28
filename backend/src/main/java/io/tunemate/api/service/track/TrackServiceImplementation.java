package io.tunemate.api.service.track;

import io.tunemate.api.model.Track;
import io.tunemate.api.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackServiceImplementation implements TrackService {
    private final TrackRepository trackRepository;

    @Autowired
    public TrackServiceImplementation(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public boolean existsBySpotifyId(String spotifyId) {
        return trackRepository.existsById(spotifyId);
    }

    @Override
    public boolean isTopTrack(String spotifyId) {
        if (this.existsBySpotifyId(spotifyId)) {
            Track foundTrack = trackRepository.findById(spotifyId).get();
            return foundTrack.getIsTopTrack() != null;
        }
        return false;
    }

    @Override
    public Track createTrack(Track track) {
        return trackRepository.save(track);
    }
}
