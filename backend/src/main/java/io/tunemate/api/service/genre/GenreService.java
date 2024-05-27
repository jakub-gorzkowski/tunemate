package io.tunemate.api.service.genre;

import io.tunemate.api.model.Genre;

public interface GenreService {
    boolean exists(String genre);
    Genre createGenre(Genre genre);
}
