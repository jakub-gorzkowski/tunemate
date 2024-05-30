package io.tunemate.api.controller;

import io.tunemate.api.dto.UserDto;
import io.tunemate.api.model.User;
import io.tunemate.api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static io.tunemate.api.mapper.UserMapper.mapToUserDto;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/get")
    public ResponseEntity<UserDto> readUserById(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(mapToUserDto(user), HttpStatus.OK);
    }

    @PatchMapping(path = "/update/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId, @RequestBody User user) {
        if (!userService.exists(userId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(userId);
        User updatedUser = userService.updateUser(userId, user);
        return new ResponseEntity<>(mapToUserDto(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/follow/{artistId}")
    public ResponseEntity<UserDto> followArtist(
            @AuthenticationPrincipal User user,
            @PathVariable("artistId") String artistId
    ) {
        User retrievedUser = userService.followArtist(user.getId(), artistId);
        return new ResponseEntity<>(mapToUserDto(retrievedUser), HttpStatus.OK);
    }
}
