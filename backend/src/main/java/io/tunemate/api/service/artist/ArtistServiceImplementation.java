package io.tunemate.api.service.artist;

import io.tunemate.api.model.Artist;
import io.tunemate.api.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistServiceImplementation implements ArtistService {
    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistServiceImplementation(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
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
        Artist artist = artistRepository.findById(spotifyId).get();
        return artist;
    }
}
