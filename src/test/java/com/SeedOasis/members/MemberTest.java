package com.SeedOasis.members;

import com.SeedOasis.auth.JwtToken;
import com.SeedOasis.auth.RedisJwtService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 저장")
    public void testCreate() {
        //Given
        Member member = Member.builder()
                .username("a12345")
                .email("test@test.com")
                .password(passwordEncoder.encode("123456789"))
                .nickname("tester")
                .role("ROLE_ADMIN")
                .build();

        //When
        Member saved = memberRepository.save(member);

        //Then
        Assertions.assertThat(saved).isNotNull();
    }

    @Autowired
    private RedisJwtService redisJwtService;

    @Test
    @DisplayName("Test")
    public void testtest() {
        JwtToken t1 = JwtToken.builder()
                .accessToken("01")
                .refreshToken("01")
                .build();

        JwtToken t2 = JwtToken.builder()
                .accessToken("03")
                .refreshToken("03")
                .build();

//        List<JwtTokenDto> jwtList = new ArrayList<>();
//        jwtList.add(t1);
//        jwtList.add(t2);


//        redisJwtService.addAll("test", jwtList);

        redisJwtService.update("test", t1, t2);
    }

    @Test
    @DisplayName("Temp")
    public void temp() {

    }
}