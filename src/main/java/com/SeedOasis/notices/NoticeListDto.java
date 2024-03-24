package com.SeedOasis.notices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NoticeListDto {
    private Long id;
    private String title;
    private LocalDateTime createdDate;
}
