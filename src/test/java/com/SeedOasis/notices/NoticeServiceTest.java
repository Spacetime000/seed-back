package com.SeedOasis.notices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticeServiceTest {

    @Autowired
    NoticeService noticeService;

    @Autowired
    NoticeRepository noticeRepository;

    @Test
    void temp() {

        List<NoticeDto> noticeDtoList = noticeService.recentNotice(5);
        System.out.println(noticeDtoList.size());

    }
}