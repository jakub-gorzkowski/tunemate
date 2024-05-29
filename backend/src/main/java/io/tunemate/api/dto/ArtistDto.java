package io.tunemate.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArtistDto {
    private String spotifyId;
    private String name;
    private String photoUrl;
    private Long followerCount;
}
