package io.tunemate.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.tunemate.api.dto.ReleaseDto;
import io.tunemate.api.dto.ReviewDto;
import io.tunemate.api.mapper.ReleaseMapper;
import io.tunemate.api.mapper.ReviewMapper;
import io.tunemate.api.model.Release;
import io.tunemate.api.model.Review;
import io.tunemate.api.model.User;
import io.tunemate.api.service.release.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

import static io.tunemate.api.mapper.ReleaseMapper.mapToReleaseDto;

@RestController
@RequestMapping(path = "/api/releases")
public class ReleaseController {
    private final ReleaseService releaseService;

    @Autowired
    ReleaseController(ReleaseService releaseService) {
        this.releaseService = releaseService;
    }

    @GetMapping(path = "/get/{releaseId}")
    public ResponseEntity<ReleaseDto> getRelease(@PathVariable String releaseId) throws JsonProcessingException {
        Release release;

        if (!releaseService.existsBySpotifyId(releaseId)) {
            release = releaseService.retrieveRelease(releaseId);
            releaseService.createRelease(release);
        } else {
            release = releaseService.findBySpotifyId(releaseId);
            if (release.getTracks().isEmpty()) {
                release = releaseService.retrieveRelease(releaseId);
                releaseService.createRelease(release);
            }
        }

        release = releaseService.findBySpotifyId(releaseId);

        return new ResponseEntity<>(mapToReleaseDto(release), HttpStatus.OK);
    }

    @GetMapping(path = "/this-week")
    public ResponseEntity<Set<ReleaseDto>> thisWeekReleases() {
        Set<Release> releases = releaseService.getThisWeekReleases();
        return new ResponseEntity<>(releases.stream()
                .map(ReleaseMapper::mapToReleaseDto)
                .collect(Collectors.toSet()), HttpStatus.OK);
    }

    @GetMapping(path = "/this-month")
    public ResponseEntity<Set<ReleaseDto>> thisMonthReleases() {
        Set<Release> releases = releaseService.getThisMonthReleases();
        return new ResponseEntity<>(releases.stream()
                .map(ReleaseMapper::mapToReleaseDto)
                .collect(Collectors.toSet()), HttpStatus.OK);
    }

    @GetMapping(path = "/reviews/{releaseId}")
    public ResponseEntity<Set<ReviewDto>> getReviews(@PathVariable String releaseId) {
        Set<Review> reviews = releaseService.getReviews(releaseId);

        return new ResponseEntity<>(reviews.stream()
                .map(ReviewMapper::mapToReviewDto)
                .collect(Collectors.toSet()), HttpStatus.OK);
    }

    @PostMapping(path = "/add-review/{releaseId}")
    public ResponseEntity<Set<ReviewDto>> postReview(
            @AuthenticationPrincipal User user,
            @PathVariable String releaseId,
            @RequestBody ReviewDto reviewDto
    ) {
        releaseService.addReview(user.getId(), releaseId, reviewDto);
        Set<Review> reviews = releaseService.getReviews(releaseId);

        return new ResponseEntity<>(reviews.stream()
                .map(ReviewMapper::mapToReviewDto)
                .collect(Collectors.toSet()), HttpStatus.OK);
    }
}
