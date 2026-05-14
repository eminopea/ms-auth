package com.template.ms_auth.config;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

//oauth2ResourceServer: sirve para configurar la aplicación como un recurso protegido por OAuth2,
//  lo que significa que solo los clientes con tokens válidos pueden acceder a los recursos protegidos.
//  En este caso, se está configurando para usar JWT (JSON Web Tokens) como el formato de token de acceso.

@Configuration
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/auth/login",
                        "/auth/register",
                        "/v3/api-docs/**",
                        "/swagger-ui/**")
                .permitAll()
                .anyRequest().authenticated())
                .oauth2ResourceServer((oauth2ResourceServer) -> oauth2ResourceServer
                .jwt((jwt) -> jwt
                .decoder(jwtDecoder())));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtDecoder jwtDecoder() {

        SecretKey secretKey = new SecretKeySpec(
                jwtSecret.getBytes(),
                "HmacSHA256");

        return NimbusJwtDecoder
                .withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }
}
