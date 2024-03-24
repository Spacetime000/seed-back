package com.SeedOasis.voca;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VocaContentDto {

    String spelling;
    String meaning;
    String example;
    String exampleMeaning;
}
