package com.SeedOasis.notices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private List<NoticeAttachmentDto> noticeAttachmentList;

    public static NoticeDto of(Notice notice) {
        NoticeDto noticeDto = NoticeDto.builder()
                .id(notice.getId())
                .content(notice.getContent())
                .title(notice.getTitle())
                .createdDate(notice.getCreatedDate())
                .noticeAttachmentList(new ArrayList<>())
                .build();

        List<NoticeAttachment> attachmentList = notice.getAttachments();

        for (NoticeAttachment noticeAttachment : attachmentList) {
            NoticeAttachmentDto build = NoticeAttachmentDto.builder()
                    .id(noticeAttachment.getId())
                    .originalName(noticeAttachment.getOriginalName())
                    .filePath(noticeAttachment.getFilePath())
                    .fileName(noticeAttachment.getFileName())
                    .fileSize(noticeAttachment.getFileSize())
                    .build();
            noticeDto.getNoticeAttachmentList().add(build);

        }

        return noticeDto;
    }

}
