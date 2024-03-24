package com.SeedOasis.notices;

import com.SeedOasis.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor
@TableGenerator(
        name = "NOTICE_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnValue = "NOTICE_SEQ",
        allocationSize = 1
)
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "NOTICE_SEQ_GENERATOR")
    private Long id;

    private String title;
    private String content;
    private Boolean status = false;

    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<NoticeAttachment> attachments = new ArrayList<>();

    @Builder
    public Notice(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addAttachment(NoticeAttachment noticeAttachment) {
        this.attachments.add(noticeAttachment);

        if (noticeAttachment.getNotice() != this) {
            noticeAttachment.setNotice(this);
        }
    }
}
