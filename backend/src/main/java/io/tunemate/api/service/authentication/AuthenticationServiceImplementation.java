package io.tunemate.api.service.authentication;

import io.tunemate.api.request.AuthenticationRequest;
import io.tunemate.api.response.AuthenticationResponse;
import io.tunemate.api.request.RegisterRequest;
import io.tunemate.api.model.User;
import io.tunemate.api.repository.UserRepository;
import io.tunemate.api.service.jwt.JwtServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImplementation implements AuthenticationService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImplementation jwtServiceImplementation;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(List.of())
                .build();

        userRepository.save(user);

        String jwtToken = jwtServiceImplementation.generateToken(user);

        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest registerRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.getEmail(),
                        registerRequest.getPassword()
                )
        );

        User user = userRepository.findByEmail(registerRequest.getEmail())
                .orElseThrow();

        String jwtToken = jwtServiceImplementation.generateToken(user);

        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }
}
