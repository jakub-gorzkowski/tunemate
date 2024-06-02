package io.tunemate.api.repository;

import io.tunemate.api.model.Artist;
import io.tunemate.api.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ArtistRepository extends JpaRepository<Artist, String> {
    Set<Artist> findArtistsByGenres(Set<Genre> genres);
    Artist findArtistsByName(String name);
}
