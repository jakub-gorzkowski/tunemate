package io.tunemate.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String photoUrl;
    private String email;
    private String spotifyId;
    private String password;
    private Set<ArtistDto> favouriteArtists;
}
