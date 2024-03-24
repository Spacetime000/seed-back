package com.SeedOasis.auth;

import com.SeedOasis.members.MemberService;
import com.SeedOasis.utils.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final MemberService memberService;
    private final AuthenticationService authenticationService;
    private final RedisJwtService redisJwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDto registerDto, BindingResult bindingResult) {

        Map<String, String> validatorResult = new HashMap<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                validatorResult.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(validatorResult);
        }

        if (!memberService.usernameDoubleCheck(registerDto.getUsername()) && !memberService.nicknameDoubleCheck(registerDto.getNickname())) {
            memberService.save(registerDto);
            return ResponseEntity.ok().build();
        } else {
            if (memberService.nicknameDoubleCheck(registerDto.getNickname()))
                validatorResult.put("nickname", "중복된 닉네임입니다.");

            if (memberService.usernameDoubleCheck(registerDto.getUsername())) {
                validatorResult.put("username", "중복된 아이디입니다.");
            }
        }

        return ResponseEntity.badRequest().body(validatorResult);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginDto loginDto) {

        try {
            return ResponseEntity.ok(authenticationService.authenticate(loginDto));

        } catch (Exception e) {

            ErrorResponse response = ErrorResponse.builder()
                    .errorMessage("등록되지 않은 아이디이거나 아이디 또는 비밀번호를 잘못 입력했습니다.")
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    //재발급
    @GetMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        JwtToken jwtToken = authenticationService.refreshToken(request);
        if (jwtToken != null) {
            return ResponseEntity.ok(jwtToken);
        }
        return ResponseEntity.badRequest().build();
    }

    //토큰 검증
    @GetMapping("/token-valid")
    public ResponseEntity<?> isTokenValid(HttpServletRequest request) {
        if (authenticationService.isTokenValid(request))
            return ResponseEntity.ok().build();

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/member-login-list")
    public ResponseEntity<?> loginMemberList() {
        Set<String> allKeys = redisJwtService.getAllKeys();
        Set<String> members = new HashSet<>();

        for (String key : allKeys) {
            String member = key.substring(key.indexOf(":") +1);
            members.add(member);
        }
        return ResponseEntity.ok(members);
    }

    @PostMapping("/forced-logout")
    public ResponseEntity<?> memberLogout(@RequestBody Map<String, String> map) {
        String id = map.get("id");
        redisJwtService.deleteAllKey(id);
        return ResponseEntity.ok().build();
    }
}
