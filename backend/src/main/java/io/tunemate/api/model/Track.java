package io.tunemate.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tracks")
public class Track {
    @Id
    @Column(name = "id")
    private String spotifyId;

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "track_number")
    private Long trackNumber;

    @Column(name = "top_track")
    private Boolean isTopTrack;

    @ManyToMany(mappedBy = "tracks")
    @JsonBackReference
    private Set<Release> releases;

    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "tracks_artists",
        joinColumns = @JoinColumn(name = "track_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id")
    )
    @JsonManagedReference
    private Set<Artist> artists;

    @ManyToMany(mappedBy = "tracks")
    private List<Playlist> playlists;
}
