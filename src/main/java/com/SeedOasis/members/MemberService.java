package com.SeedOasis.members;

import com.SeedOasis.auth.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 회원가입
     * @param registerDto
     */
    public void save(RegisterDto registerDto) {

        Member member = Member.builder()
                .username(registerDto.getUsername())
                .password(bCryptPasswordEncoder.encode(registerDto.getPassword()))
                .nickname(registerDto.getNickname())
                .email(registerDto.getEmail())
                .role("ROLE_USER")
                .build();

        memberRepository.save(member);
    }

    /**
     * nickname(닉네임) 중복 체크
     * @param nickname
     * @return 중복이면 true, 중복이 아니면 false를 리턴한다.
     */
    public boolean nicknameDoubleCheck(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    /**
     * username(아이디) 중복 체크
     * @param username 회원의 아이디
     * @return 중복이면 true, 중복이 아니면 false를 리턴한다.
     */
    public boolean usernameDoubleCheck(String username) {
        return memberRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username).orElseThrow();
    }
}
