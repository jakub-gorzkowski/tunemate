package io.tunemate.api.controller;

import io.tunemate.api.dto.UserDto;
import io.tunemate.api.model.User;
import io.tunemate.api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.tunemate.api.mapper.UserMapper.mapToUserDto;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {
    private UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<UserDto> readUser(@PathVariable("id") Long userId) {
        UserDto userDto = userService.findUserById(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
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
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
