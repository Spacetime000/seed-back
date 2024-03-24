package com.SeedOasis.notices;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NoticeListAdminDto {
    private Long id;
    private String title;
    private LocalDateTime createdDate;
    private Boolean status;

}
