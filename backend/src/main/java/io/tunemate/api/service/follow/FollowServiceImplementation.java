package io.tunemate.api.service.follow;

import io.tunemate.api.dto.UserDto;
import io.tunemate.api.mapper.UserMapper;
import io.tunemate.api.model.Follow;
import io.tunemate.api.model.User;
import io.tunemate.api.repository.FollowRepository;
import io.tunemate.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FollowServiceImplementation implements FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Autowired
    public FollowServiceImplementation(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Set<UserDto> getFollowers(Long userId) {
        User user = userRepository.findById(userId).get();
        Set<Follow> followers = followRepository.findAllByFollowedUser(user);
        return followers.stream()
                .map(Follow::getUser)
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<UserDto> getFollowing(Long userId) {
        User user = userRepository.findById(userId).get();
        Set<Follow> following = followRepository.findAllByUser(user);
        return following.stream()
                .map(Follow::getFollowedUser)
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Follow followUser(Long userId, Long userToFollowId) {
        User user = userRepository.findById(userId).get();
        User userToFollow = userRepository.findById(userToFollowId).get();

        Follow follow = followRepository.save(new Follow());
        follow.setUser(user);
        follow.setFollowedUser(userToFollow);
        follow = followRepository.save(follow);
        return follow;
    }

    @Override
    public Follow unfollowUser(Long userId, Long userToUnfollowId) {
        User user = userRepository.findById(userId).get();
        User userToUnfollow = userRepository.findById(userToUnfollowId).get();

        Follow follow = followRepository.findByUser(user);
        follow.setUser(null);
        follow.setFollowedUser(null);
        followRepository.delete(follow);

        return follow;
    }
}
