package io.tunemate.api.service.release;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.tunemate.api.dto.ReviewDto;
import io.tunemate.api.model.Release;
import io.tunemate.api.model.Review;

import java.util.Set;

public interface ReleaseService {
    boolean existsBySpotifyId(String spotifyId);
    Release findBySpotifyId(String spotifyId);
    Release createRelease(Release release);
    Release retrieveRelease(String releaseId) throws JsonProcessingException;
    Set<Release> getThisWeekReleases();
    Set<Release> getThisMonthReleases();
    void addReview(Long userId, String releaseId, ReviewDto review);
    Set<Review> getReviews(String releaseId);
}
