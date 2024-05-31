package io.tunemate.api.controller;

import io.tunemate.api.dto.UserDto;
import io.tunemate.api.model.Follow;
import io.tunemate.api.model.User;
import io.tunemate.api.service.follow.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "/api/follow")
public class FollowController {
    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping(path = "/followers")
    public ResponseEntity<Set<UserDto>> followers(@AuthenticationPrincipal User user) {
        Set<UserDto> followers = followService.getFollowers(user.getId());
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }

    @GetMapping(path = "/following")
    public ResponseEntity<Set<UserDto>> following(@AuthenticationPrincipal User user) {
        Set<UserDto> following = followService.getFollowing(user.getId());
        return new ResponseEntity<>(following, HttpStatus.OK);
    }

    @PatchMapping(path = "/follow/{userId}")
    public ResponseEntity<Follow> followUser(
            @AuthenticationPrincipal User user,
            @PathVariable("userId") Long userId
    ) {
        Follow follow = followService.followUser(user.getId(), userId);
        return new ResponseEntity<>(follow, HttpStatus.OK);
    }

    @PatchMapping(path = "/unfollow/{userId}")
    public ResponseEntity<Follow> unfollowUser(
            @AuthenticationPrincipal User user,
            @PathVariable("userId") Long userId
    ) {
        Follow follow = followService.unfollowUser(user.getId(), userId);
        return new ResponseEntity<>(follow, HttpStatus.OK);
    }
}
