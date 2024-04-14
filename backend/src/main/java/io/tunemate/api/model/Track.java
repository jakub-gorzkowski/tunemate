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
@Table(name = "tracks")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "spotify_id")
    private Long spotifyId;

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private Long duration;

    @ManyToMany(mappedBy = "tracks")
    private List<Release> releases;

    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "tracks_artists",
        joinColumns = @JoinColumn(name = "track_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id")
    )
    private List<Artist> artists;

    @ManyToMany(mappedBy = "tracks")
    private List<Playlist> playlists;

    @ManyToMany(mappedBy = "tracks")
    private List<Genre> genres;
}
