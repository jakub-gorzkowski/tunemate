package io.tunemate.api.service.follow;

import io.tunemate.api.dto.UserDto;
import io.tunemate.api.model.Follow;

import java.util.Set;

public interface FollowService {
    Set<UserDto> getFollowers(Long userId);
    Set<UserDto> getFollowing(Long userId);
    Follow followUser(Long userId, Long userToFollowId);
    Follow unfollowUser(Long userId, Long userToUnfollowId);
}
