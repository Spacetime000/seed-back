package com.SeedOasis.notices;

import com.SeedOasis.utils.FileService;
import com.SeedOasis.utils.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final FileService fileService;
    private final NoticeAttachmentRepository noticeAttachmentRepository;
    private final static String PATH = "/notice";

    public void saveNotice(NoticeFormDto noticeFormDto) throws IOException {
        Notice notice = Notice.builder()
                .title(noticeFormDto.getTitle())
                .content(noticeFormDto.getContent())
                .build();

        noticeRepository.save(notice);

        MultipartFile[] files = noticeFormDto.getFiles();

        if (files != null) {
            for (MultipartFile file : files) {

                UploadFile uploaded = fileService.upload(file, PATH);
                NoticeAttachment attachment = NoticeAttachment.builder()
                        .originalName(uploaded.getOriginalName())
                        .fileName(uploaded.getFileName())
                        .fileSize(uploaded.getFileSize())
                        .filePath(uploaded.getFilePath()).build();

                notice.addAttachment(attachment);
                noticeAttachmentRepository.save(attachment);
            }
        }

    }

    public List<NoticeListDto> generateNoticeListDto() {

        List<Notice> notices = noticeRepository.findNotices(true);
        List<NoticeListDto> noticeListDtoList = new ArrayList<>();

        for (Notice notice : notices) {
            NoticeListDto build = NoticeListDto.builder()
                    .id(notice.getId())
                    .title(notice.getTitle())
                    .createdDate(notice.getCreatedDate())
                    .build();
            noticeListDtoList.add(build);
        }

        return noticeListDtoList;

    }

    //map
    public List<NoticeListAdminDto> generateNoticeListAdminDto() {
        List<Notice> notices = noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));;
        List<NoticeListAdminDto> noticeListAdminDtoList = new ArrayList<>();

        for (Notice notice : notices) {
            NoticeListAdminDto build = NoticeListAdminDto.builder()
                    .id(notice.getId())
                    .title(notice.getTitle())
                    .createdDate(notice.getCreatedDate())
                    .status(notice.getStatus())
                    .build();
            noticeListAdminDtoList.add(build);
        }

        return noticeListAdminDtoList;
    }

    @Transactional
    public NoticeDto generateNotice(Long id) {
        Notice notice = noticeRepository.findById(id).orElse(null);

        if (notice != null) {
            return NoticeDto.of(notice);
        }

        return null;
    }

    @Transactional
    public NoticeDto generatePublicNotice(Long id) {
        Notice notice = noticeRepository.findByIdAndStatus(id, true).orElse(null);

        if (notice != null) {
            return NoticeDto.of(notice);
        }

        return null;
    }

    @Transactional
    public void toggleVisibility(Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow();
        notice.setStatus(!notice.getStatus());
    }

    @Transactional
    public void editNotice(NoticeEditDto noticeEditDto) throws IOException {
        noticeAttachmentRepository.deleteAllById(noticeEditDto.getRemoveAttachmentIds()); //첨부파일 제거
        Notice notice = noticeRepository.findById(noticeEditDto.getId()).orElseThrow();
        notice.setTitle(noticeEditDto.getTitle());
        notice.setContent(noticeEditDto.getContent());

        for (MultipartFile multipartFile : noticeEditDto.getFiles()) {
            UploadFile upload = fileService.upload(multipartFile, PATH);
            NoticeAttachment noticeAttachment = NoticeAttachment.of(upload);
            notice.addAttachment(noticeAttachment);
            noticeAttachmentRepository.save(noticeAttachment);
        }

        noticeRepository.save(notice);

    }

    public void deleteNotice(Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow();

        for (NoticeAttachment noticeAttachment : notice.getAttachments()) {
            fileService.delete(noticeAttachment.getFilePath());
        }

//        noticeAttachmentRepository.deleteAll(notice.getAttachments());
        noticeRepository.delete(notice);
    }

    @Transactional
    public List<NoticeDto> recentNotice(Integer count) {
        Pageable pageable = Pageable.ofSize(count);
        List<Notice> notices = noticeRepository.findLastNotices(pageable);
        List<NoticeDto> noticeDtoList = new ArrayList<>();

        for (Notice notice : notices) {
            noticeDtoList.add(NoticeDto.of(notice));
        }

        return noticeDtoList;
    }


}
