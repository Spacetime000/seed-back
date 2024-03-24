package com.SeedOasis.notices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeAttachmentDto {

    private Long id;
    private String originalName;
    private String fileName;
    private String filePath;
    private Long fileSize;
}
