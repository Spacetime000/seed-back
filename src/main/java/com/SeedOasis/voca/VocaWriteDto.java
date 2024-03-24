package com.SeedOasis.voca;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VocaWriteDto {

    private Long id;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    private List<VocaContentDto> contents;

}
