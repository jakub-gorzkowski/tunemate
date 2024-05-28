package io.tunemate.api.service.genre;

import io.tunemate.api.model.Genre;
import io.tunemate.api.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenreServiceImplementation implements GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    GenreServiceImplementation(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public boolean exists(String genre) {
        return genreRepository.existsById(genre);
    }

    @Override
    public Genre findByName(String name) {
        Optional<Genre> genre = genreRepository.findById(name);
        return genre.orElse(null);
    }

    @Override
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }
}
