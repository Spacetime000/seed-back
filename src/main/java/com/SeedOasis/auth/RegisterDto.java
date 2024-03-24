package com.SeedOasis.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {

    @Pattern(regexp = "^[a-z0-9]{6,20}$", message = "영소문자와 숫자로 6~20자 이내로 입력해주세요.")
    @NotBlank(message = "필수입력사항입니다.")
    private String username;

    @NotBlank(message = "필수입력사항입니다.")
    @Size(min = 9, max = 20)
    private String password;

    @NotBlank(message = "필수입력사항입니다.")
    @Email(message = "이메일 형식에 맞추어 입력해주세요.")
    private String email;

    @NotBlank(message = "필수입력사항입니다.")
    @Size(min = 2, max = 10, message = "2~10자 이내로 입력해주세요.")
    private String nickname;
}
