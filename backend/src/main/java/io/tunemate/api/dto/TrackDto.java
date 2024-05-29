package io.tunemate.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class TrackDto {
    private String spotifyId;
    private String title;
    private Long duration;
    private Set<ArtistDto> artists;
}
