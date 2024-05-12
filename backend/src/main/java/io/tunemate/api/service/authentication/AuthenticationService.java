package io.tunemate.api.service.authentication;

import io.tunemate.api.request.AuthenticationRequest;
import io.tunemate.api.response.AuthenticationResponse;
import io.tunemate.api.request.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest registerRequest);
    AuthenticationResponse authenticate(AuthenticationRequest registerRequest);
}
