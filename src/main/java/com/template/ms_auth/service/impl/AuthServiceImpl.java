package com.template.ms_auth.service.impl;

import org.openapitools.model.AuthResponse;
import org.openapitools.model.LoginRequest;
import org.openapitools.model.RegisterRequest;
import org.openapitools.model.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.template.ms_auth.document.UserDocument;
import com.template.ms_auth.repository.UserRepository;
import com.template.ms_auth.service.AuthService;
import com.template.ms_auth.service.JwtService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        UserDocument user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        boolean valid = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!valid) {
            throw new RuntimeException("Password inválido");
        }

        String token = jwtService.generateToken(user.getUsername());

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setType("Bearer");

        return response;
    }

    @Override
    public UserResponse register(RegisterRequest request) {
        repository.findByUsername(request.getUsername())
                .ifPresent(user -> {
                    throw new RuntimeException("El usuario ya existe");
                });

        UserDocument user = UserDocument.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(request.getRoles())
                .build();

        UserDocument saved = repository.save(user);

        UserResponse response = new UserResponse();
        response.setId(saved.getId());
        response.setUsername(saved.getUsername());
        response.setRoles(saved.getRoles());

        return response;
    }

}
