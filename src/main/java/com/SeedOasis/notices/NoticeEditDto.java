package com.SeedOasis.notices;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeEditDto {

    @NotNull(message = "ID는 필수입니다.")
    private Long id;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    private String content;

    //    private MultipartFile[] files;
    private List<MultipartFile> files = new ArrayList<>();

//    private Long[] removeAttachmentIds;
    private List<Long> removeAttachmentIds;

}
