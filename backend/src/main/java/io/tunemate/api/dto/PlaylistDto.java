package io.tunemate.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlaylistDto {
    private String spotifyId;
    private String title;
    private String photoUrl;
    private List<TrackDto> tracks;
}
