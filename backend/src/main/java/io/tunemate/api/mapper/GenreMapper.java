package io.tunemate.api.mapper;

import io.tunemate.api.dto.GenreDto;
import io.tunemate.api.model.Genre;

public class GenreMapper {
    public static GenreDto mapToGenreDto(Genre genre) {
        return GenreDto.builder()
                .genre(genre.getGenre())
                .build();
    }
}
