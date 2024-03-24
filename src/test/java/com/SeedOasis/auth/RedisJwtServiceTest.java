package com.SeedOasis.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SpringBootTest
class RedisJwtServiceTest {

    @Autowired
    private RedisJwtService redisJwtService;

    @Autowired
    private RedisTemplate<String, JwtToken> redisTemplate;


    @BeforeEach
    void setUp() {
        redisTemplate.getConnectionFactory().getConnection().serverCommands().flushDb();
    }


    @Test
    @DisplayName("토큰 저장 및 조회")
    void getAndAdd() {
        //given
        String key = "test";
        JwtToken token = JwtToken.builder()
                .accessToken("at1")
                .refreshToken("rt1")
                .build();

        //when
        redisJwtService.add(key, token);

        //then
        List<JwtToken> tokens = redisJwtService.getTokens(key);
        Assertions.assertThat(tokens).isNotNull().hasSize(1);
        Assertions.assertThat(tokens.get(0).getAccessToken()).isEqualTo("at1");
        Assertions.assertThat(tokens.get(0).getRefreshToken()).isEqualTo("rt1");

    }

    @Test
    @DisplayName("수정")
    void update() {
        //given
        String key = "test";
        JwtToken token1 = JwtToken.builder()
                .accessToken("a1")
                .refreshToken("r1")
                .build();

        JwtToken token2 = JwtToken.builder()
                .accessToken("a2")
                .refreshToken("r2")
                .build();

        JwtToken token3 = JwtToken.builder()
                .accessToken("a3")
                .refreshToken("r3")
                .build();

        JwtToken updateToken = JwtToken.builder()
                .accessToken("a4")
                .refreshToken("r4")
                .build();

        List<JwtToken> result = new ArrayList<>();
        result.add(token1);
        result.add(token3);
        result.add(updateToken);

        //기존의 데이터
        redisJwtService.add(key, token1);
        redisJwtService.add(key, token2);
        redisJwtService.add(key, token3);

        //when
        redisJwtService.update(key, token2, updateToken);

        //then
        List<JwtToken> tokens = redisJwtService.getTokens(key);
        Assertions.assertThat(tokens).isEqualTo(result);
    }

    @Autowired
    JwtService jwtService;

    @Test
    @DisplayName("임시")
    void temp() {
        String test = "eyJhbGciOiJIUzI1NiJ9.eyJuaWNrbmFtZSI6IuydtOqyg-ydgCDri4nrhKTsnoQiLCJwcm9maWxlSW1nIjoiL2ltZy9hdmF0YXIuc3ZnIiwic3ViIjoidGVzdDEyMzEyMyIsImlhdCI6MTcwNTE2Njc4NCwiZXhwIjoxNzA1MTY3Mzg0fQ.LyuFkQRKw9tFfHEVcS8pyNPLzHA8Q8qqBnAWKs4Tpr0";
        String test2 = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIzMTIzIiwiaWF0IjoxNzA1MTY2Nzg0LCJleHAiOjE3MDUyNTMxODR9.5UH683bTMesAWJNCTWujJh56-0Tog87cO5p8zLZPzVA";

        System.out.println(jwtService.isTimeRemaining(test));
        System.out.println(jwtService.isTimeRemaining(test2));

    }


}