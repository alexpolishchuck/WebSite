package com.groupProject.backend.security;
import com.auth0.AuthenticationController;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private final SecurityConfig securityConfig;

    public AuthenticationController createAuthenticationController() {
        // JwkProvider required for RS256 tokens. If using HS256, do not use.
        JwkProvider jwkProvider = new JwkProviderBuilder(securityConfig.getDomain()).build();
        return AuthenticationController.newBuilder(securityConfig.getDomain(),
                        securityConfig.getClientId(),
                        securityConfig.getClientSecret())
                .withJwkProvider(jwkProvider)
                .build();
    }
}
