package com.template.ms_auth.service;

import org.openapitools.model.AuthResponse;
import org.openapitools.model.LoginRequest;
import org.openapitools.model.RegisterRequest;
import org.openapitools.model.UserResponse;

public interface AuthService {
    public AuthResponse login(LoginRequest request);
    public UserResponse register(RegisterRequest request);
}
