package io.tunemate.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.tunemate.api.dto.ReleaseDto;
import io.tunemate.api.model.Release;
import io.tunemate.api.service.release.ReleaseService;
import io.tunemate.api.service.spotify.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.tunemate.api.mapper.ReleaseMapper.mapToReleaseDto;

@RestController
@RequestMapping(path = "/api/releases")
public class ReleaseController {
    private final SpotifyService spotifyService;
    private final ReleaseService releaseService;

    @Autowired
    ReleaseController(SpotifyService spotifyService, ReleaseService releaseService) {
        this.spotifyService = spotifyService;
        this.releaseService = releaseService;
    }

    @GetMapping(path = "/get/{releaseId}")
    public ResponseEntity<ReleaseDto> getRelease(@PathVariable String releaseId) throws JsonProcessingException {
        Release release;

        if (!releaseService.existsBySpotifyId(releaseId)) {
            release = spotifyService.retrieveRelease(releaseId);
            releaseService.createRelease(release);
        } else {
            release = releaseService.findBySpotifyId(releaseId);
            if (release.getTracks().isEmpty()) {
                release = spotifyService.retrieveRelease(releaseId);
                releaseService.createRelease(release);
            }
        }

        release = releaseService.findBySpotifyId(releaseId);

        return new ResponseEntity<>(mapToReleaseDto(release), HttpStatus.OK);
    }
}
