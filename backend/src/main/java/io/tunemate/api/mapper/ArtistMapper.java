package io.tunemate.api.mapper;

import io.tunemate.api.dto.ArtistDto;
import io.tunemate.api.model.Artist;

public class ArtistMapper {
    public static ArtistDto mapToArtistDto(Artist artist) {
        return ArtistDto.builder()
                .spotifyId(artist.getSpotifyId())
                .name(artist.getName())
                .photoUrl(artist.getPhotoUrl())
                .followerCount(artist.getFollowerCount())
                .build();
    }
}
