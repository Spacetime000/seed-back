package com.SeedOasis.notices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@SpringBootTest
class NoticeRepositoryTest {

    @Autowired
    NoticeRepository noticeRepository;

    @Test
    @DisplayName("갯수")
    void getNoticeList() {
        List<Notice> noticeList = noticeRepository.findNotices(false);
        System.out.println(noticeList.size());
    }

    @Test
    @DisplayName("5개")
    void last5Notice() {
        List<Notice> list = noticeRepository.findLast5Notices();
        System.out.println(list.size());
    }

    @Test
    @DisplayName("last")
    void last() {
        Pageable pageable = Pageable.ofSize(5);
        List<Notice> list = noticeRepository.findLastNotices(pageable);

        System.out.println(list.size());
        for (Notice n : list) {
            System.out.println(n.getId());
        }
    }
}