package io.tunemate.api.mapper;

import io.tunemate.api.dto.ArtistDto;
import io.tunemate.api.dto.ReleaseDto;
import io.tunemate.api.dto.TrackDto;
import io.tunemate.api.model.Release;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ReleaseMapper {
    public static ReleaseDto mapToReleaseDto(Release release) {
        Set<ArtistDto> artistDtos = new HashSet<>();
        if (release.getArtists() != null) {
            artistDtos = release.getArtists().stream()
                    .map(ArtistMapper::mapToArtistDto)
                    .collect(Collectors.toSet());
        }

        Set<TrackDto> trackDtos = new HashSet<>();
        if (release.getTracks() != null) {
            trackDtos = release.getTracks().stream()
                    .map(TrackMapper::mapToTrackDto)
                    .collect(Collectors.toSet());
        }

        return ReleaseDto.builder()
                .spotifyId(release.getSpotifyId())
                .title(release.getTitle())
                .photoUrl(release.getPhotoUrl())
                .totalTracks(release.getTotalTracks())
                .releaseDate(release.getReleaseDate())
                .artists(artistDtos)
                .tracks(trackDtos)
                .build();
    }
}
