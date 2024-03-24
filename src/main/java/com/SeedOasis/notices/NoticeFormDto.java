package com.SeedOasis.notices;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeFormDto {

    private Long id;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    private String content;

    private MultipartFile[] files;

}
