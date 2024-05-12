package io.tunemate.api.controller;

import io.tunemate.api.request.AuthenticationRequest;
import io.tunemate.api.response.AuthenticationResponse;
import io.tunemate.api.service.authentication.AuthenticationServiceImplementation;
import io.tunemate.api.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImplementation authenticationServiceImplementation;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationServiceImplementation.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationServiceImplementation.authenticate(request));
    }
}
