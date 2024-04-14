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
@Table(name = "playlists")
public class Playlist {
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "playlists_tracks",
        joinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "track_id", referencedColumnName = "id")
    )
    private List<Track> tracks;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "playlists_users",
            joinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private List<User> users;
}
