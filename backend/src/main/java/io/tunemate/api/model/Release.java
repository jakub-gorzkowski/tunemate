package io.tunemate.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "releases")
public class Release {
    @Id
    @Column(name = "id")
    private String spotifyId;

    @Column(name = "title")
    private String title;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "total_tracks")
    private Long totalTracks;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @OneToMany(mappedBy = "release", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @ManyToMany(mappedBy = "releases")
    @JsonBackReference
    private Set<Artist> artists;

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
