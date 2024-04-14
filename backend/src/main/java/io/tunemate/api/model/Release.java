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
@Table(name = "releases")
public class Release {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "spotify_id")
    private String spotifyId;

    @Column(name = "title")
    private String title;

    @Column(name = "photo_url")
    private String photo_url;

    @Column(name = "total_tracks")
    private Long totalTracks;

    @OneToMany(mappedBy = "release", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @ManyToMany(mappedBy = "releases")
    private List<Artist> artists;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "releases_tracks",
        joinColumns = @JoinColumn(name = "release_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "track_id", referencedColumnName = "id")
    )
    private List<Track> tracks;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "releases_users",
            joinColumns = @JoinColumn(name = "release_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private List<User> users;
}
