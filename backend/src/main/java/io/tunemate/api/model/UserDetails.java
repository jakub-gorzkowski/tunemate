package io.tunemate.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (name = "users_details")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "spotify_url")
    private String spotifyUrl;

    @Column(name = "tidal_url")
    private String tidalUrl;

    @Column(name = "deezer_url")
    private String deezerUrl;

    @Column(name = "soundcloud_url")
    private String soundcloudUrl;

    @OneToOne(mappedBy = "userDetails", fetch = FetchType.EAGER)
    private User user;
}
