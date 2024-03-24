package com.SeedOasis.review;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewResponseDto {

    @NotNull
    private Long id;

    @NotNull
    private Integer day;
}
