package com.template.ms_auth.controller;

import org.openapitools.api.AuthApi;
import org.openapitools.model.AuthResponse;
import org.openapitools.model.LoginRequest;
import org.openapitools.model.RegisterRequest;
import org.openapitools.model.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.template.ms_auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<AuthResponse> login(LoginRequest loginRequest) {

        return ResponseEntity.ok(
                authService.login(loginRequest)
        );
    }

    @Override
    public ResponseEntity<Void> validateToken() {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UserResponse> register(RegisterRequest registerRequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.register(registerRequest));
    }
}
