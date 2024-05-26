package io.tunemate.api.service.release;

import io.tunemate.api.model.Release;
import io.tunemate.api.repository.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReleaseServiceImplementation implements ReleaseService {
    private final ReleaseRepository releaseRepository;

    @Autowired
    public ReleaseServiceImplementation(ReleaseRepository releaseRepository) {
        this.releaseRepository = releaseRepository;
    }

    @Override
    public boolean existsBySpotifyId(String spotifyId) {
        return releaseRepository.existsById(spotifyId);
    }

    @Override
    public Release createRelease(Release release) {
        return releaseRepository.save(release);
    }
}
