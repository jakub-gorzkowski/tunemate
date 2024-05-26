package io.tunemate.api.model;

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
@Table(name = "artists")
public class Artist {
    @Id
    @Column(name = "id")
    private String spotifyId;

    @Column(name = "name")
    private String name;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "follower_count")
    private Long followerCount;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "artists_releases",
        joinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "release_id", referencedColumnName = "id")
    )
    @JsonManagedReference
    private Set<Release> releases;

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
