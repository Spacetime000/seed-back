package com.SeedOasis.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class RedisJwtService {

    private final RedisTemplate<String, JwtToken> redisTemplate;
    private final static String REDIS_KEY_PREFIX = "jwt:";

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public List<JwtToken> getTokens(String username) {
        String key = getKey(username);
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(JwtToken.class));
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public Set<String> getAllKeys() {
        return redisTemplate.keys(REDIS_KEY_PREFIX + "*");
    }

    public boolean existsByAccessToken(String username, String accessToken) {
        return getTokens(username)
                .stream()
                .anyMatch(token -> token.getAccessToken().equals(accessToken));
    }

    public boolean existsByRefreshToken(String username, String refreshToken) {
        return getTokens(username)
                .stream()
                .anyMatch(token -> token.getRefreshToken().equals(refreshToken));
    }

    public Optional<JwtToken> getTokenByAccessToken(String username, String accessToken) {
        return getTokens(username)
                .stream()
                .filter(token -> token.getAccessToken().equals(accessToken))
                .findFirst();
    }

    public Optional<JwtToken> getTokenByRefreshToken(String username, String refreshToken) {
        return getTokens(username)
                .stream()
                .filter(token -> token.getRefreshToken().equals(refreshToken))
                .findFirst();
    }

    public void add(String username, JwtToken value) {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(value.getClass()));
        String key = getKey(username);
        redisTemplate.opsForList().rightPush(key, value);
        redisTemplate.expire(key, refreshExpiration, TimeUnit.MILLISECONDS);
    }

    public void addAll(String username, List<JwtToken> jwtTokenList) {
        jwtTokenList.forEach(e -> {
            add(username, e);
        });
    }

    //재발급시 사용
    public void update(String username, JwtToken oldValue, JwtToken newValue) {
        List<JwtToken> tokens = getTokens(username);
        tokens.remove(oldValue);
        tokens.add(newValue);
        deleteAllKey(username);
        addAll(username, tokens);
    }

    public void delete(String username, JwtToken jwtToken) {
        List<JwtToken> tokens = getTokens(username);
        tokens.remove(jwtToken);
        deleteAllKey(username);
        addAll(username, tokens);
    }
/*
    public void deleteByAccessToken(String username, String accessToken) {
        Optional<JwtTokenDto> token = getTokenByAccessToken(username, accessToken);
        if (token.isPresent()) {
            List<JwtTokenDto> tokens = getTokens(username);
            tokens.remove(token.get());
            deleteAllKey(username);
            addAll(username, tokens);
        }
    }

    public void deleteByRefreshToken(String username, String refreshToken) {
        Optional<JwtTokenDto> token = getTokenByRefreshToken(username, refreshToken);
        if (token.isPresent()) {
            List<JwtTokenDto> tokens = getTokens(username);
            tokens.remove(token.get());
            deleteAllKey(username);
            addAll(username, tokens);
        }
    }
*/
    public void deleteAllKey(String username) {
        redisTemplate.delete(getKey(username));
    }

    private String getKey(String key) {
        return REDIS_KEY_PREFIX + key;
    }
}
