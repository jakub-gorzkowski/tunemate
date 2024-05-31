package io.tunemate.api.repository;

import io.tunemate.api.model.Follow;
import io.tunemate.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Set<Follow> findAllByUser(User user);
    Set<Follow> findAllByFollowedUser(User user);
    Follow findByUser(User user);
}
