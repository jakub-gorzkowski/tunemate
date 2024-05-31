package io.tunemate.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @Column(name = "id")
    private String genre;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "genres_artists",
            joinColumns = @JoinColumn(name = "genre", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id")
    )
    @JsonManagedReference
    private Set<Artist> artists;

    @ManyToMany(mappedBy = "genres", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<User> users;
}
