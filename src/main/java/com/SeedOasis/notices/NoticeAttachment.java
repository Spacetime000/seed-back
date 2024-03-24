package com.SeedOasis.notices;

import com.SeedOasis.utils.UploadFile;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor
@TableGenerator(
        name = "NOTICE_ATTACHMENT_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnValue = "NOTICE_ATTACHMENT_SEQ",
        allocationSize = 1
)
public class NoticeAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "NOTICE_ATTACHMENT_SEQ_GENERATOR")
    private Long id;

    private String originalName;
    private String fileName;
    private String filePath;
    private Long fileSize;

    @ManyToOne
    @JoinColumn(name = "notice_id")
    private Notice notice;

    public void setNotice(Notice notice) {

        if (this.notice != null) {
            this.notice.getAttachments().remove(this);
        }

        this.notice = notice;

        if (!notice.getAttachments().contains(this)) {
            notice.getAttachments().add(this);
        }
    }

    @Builder
    public NoticeAttachment(String originalName, String fileName, String filePath, Long fileSize) {
        this.originalName = originalName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public static NoticeAttachment of(UploadFile uploadFile) {
        return NoticeAttachment.builder()
                .originalName(uploadFile.getOriginalName())
                .fileName(uploadFile.getFileName())
                .filePath(uploadFile.getFilePath())
                .fileSize(uploadFile.getFileSize())
                .build();

    }
}
