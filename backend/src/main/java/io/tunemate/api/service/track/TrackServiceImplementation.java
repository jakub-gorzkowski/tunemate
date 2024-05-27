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
    public Track createTrack(Track track) {
        return trackRepository.save(track);
    }
}
