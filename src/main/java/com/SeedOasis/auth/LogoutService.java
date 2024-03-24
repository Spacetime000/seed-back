package com.SeedOasis.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final RedisJwtService redisJwtService;
    private final JwtService jwtService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            return;
        }
        jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);
        redisJwtService.getTokenByRefreshToken(username, jwt).ifPresent(token -> {
            redisJwtService.delete(username, token);
            SecurityContextHolder.clearContext();
        });
    }
}
