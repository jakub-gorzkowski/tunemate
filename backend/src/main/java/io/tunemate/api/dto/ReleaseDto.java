package io.tunemate.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class ReleaseDto {
    private String spotifyId;
    private String title;
    private String photoUrl;
    private Long totalTracks;
    private LocalDate releaseDate;
    private Set<ArtistDto> artists;
    private Set<TrackDto> tracks;
}
