package io.tunemate.api.mapper;

import io.tunemate.api.dto.TrackDto;
import io.tunemate.api.model.Track;

import java.util.stream.Collectors;

public class TrackMapper {
    public static TrackDto mapToTrackDto(Track track) {
        return TrackDto.builder()
                .spotifyId(track.getSpotifyId())
                .title(track.getTitle())
                .duration(track.getDuration())
                .artists(track.getArtists().stream()
                        .map(ArtistMapper::MapToArtistDto)
                        .collect(Collectors.toSet()))
                .build();
    }
}
