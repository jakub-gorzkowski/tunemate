package io.tunemate.api.mapper;

import io.tunemate.api.dto.PlaylistDto;
import io.tunemate.api.dto.TrackDto;
import io.tunemate.api.model.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlaylistMapper {
    public static PlaylistDto mapToPlaylistDto(Playlist playlist) {
        List<TrackDto> trackDtos = new ArrayList<>();
        if (playlist.getTracks() != null) {
            trackDtos = playlist.getTracks().stream()
                    .map(TrackMapper::mapToTrackDto)
                    .toList();
        }

        return PlaylistDto.builder()
                .spotifyId(playlist.getSpotifyId())
                .title(playlist.getTitle())
                .photoUrl(playlist.getPhotoUrl())
                .tracks(trackDtos)
                .build();
    }
}
