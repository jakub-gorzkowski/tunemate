package io.tunemate.api.repository;

import io.tunemate.api.model.Release;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReleaseRepository extends JpaRepository<Release, String> {
}
