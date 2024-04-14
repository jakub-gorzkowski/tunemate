package io.tunemate.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "spotify_id")
    private String spotifyId;

    @Column(name = "name")
    private String name;

    @Column(name = "photo_url")
    private String photo_url;

    @Column(name = "listener_count")
    private Long listenerCount;

    @Column(name = "follower_count")
    private Long followerCount;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "artists_releases",
        joinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "release_id", referencedColumnName = "id")
    )
    private List<Release> releases;

    @ManyToMany(mappedBy = "artists", cascade = CascadeType.ALL)
    private List<Track> tracks;

    @ManyToMany(mappedBy = "artists", cascade = CascadeType.ALL)
    private List<Genre> genres;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "artists_users",
            joinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private List<User> users;
}
