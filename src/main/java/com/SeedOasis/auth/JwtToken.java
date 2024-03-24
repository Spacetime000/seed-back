package com.SeedOasis.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {
    private String accessToken;
    private String refreshToken;
}
