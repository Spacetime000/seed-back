package com.SeedOasis.auth;

import com.SeedOasis.members.Member;
import com.SeedOasis.members.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final RedisJwtService redisJwtService;

    public JwtToken authenticate(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );

        Member member = memberRepository.findByUsername(loginDto.getUsername()).orElseThrow();
        Map<String, Object> map = new HashMap<>();
        map.put("nickname", member.getNickname());
        map.put("profileImg", member.getProfileImg());
        map.put("role", member.getRole());

        String accessToken = jwtService.generateToken(map, member);
        String refreshToken = jwtService.generateRefreshToken(member);
        JwtToken token = JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        redisJwtService.add(member.getUsername(), token);
        return token;
    }

    public JwtToken refreshToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        JwtToken oldToken = null;

        if (redisJwtService.existsByRefreshToken(username, token)) { //로그아웃 여부 체크
            Member member = memberRepository.findByUsername(username).orElseThrow();

            //토큰 유효성 체크
            if (jwtService.isTokenValid(token, member)) {

                //재발급 전 기존 토큰
                oldToken = redisJwtService.getTokenByRefreshToken(username, token).orElseThrow();

                //재발급
                Map<String, Object> map = new HashMap<>();
                map.put("nickname", member.getNickname());
                map.put("profileImg", member.getProfileImg());
                map.put("role", member.getRole());
                String accessToken = jwtService.generateToken(map, member);
                String refreshToken = token; //기존 refresh 토큰

                //refresh 토큰 남은 시간
                if (jwtService.isTimeRemaining(token)) { //1시간 이하
                    refreshToken = jwtService.generateRefreshToken(member); //재발급 refresh 토큰
                }

                JwtToken build = JwtToken.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                //redis 변경처리
                redisJwtService.update(username, oldToken, build);
                return build;
            }
        }

        return null;
    }

    public boolean isTokenValid(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false;
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        Member member = memberRepository.findByUsername(username).orElseThrow();

        return jwtService.isTokenValid(token, member);
    }
}
