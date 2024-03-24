package com.SeedOasis.notices;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    /**
     * 공지사항 리스트 반환
     * @param status true - 공개, false - 비공개
     * @return
     */
    @Query("SELECT n FROM Notice n WHERE n.status = :status ORDER BY n.createdDate DESC")
    List<Notice> findNotices(@Param("status") boolean status);

    Optional<Notice> findByIdAndStatus(Long id, Boolean status);

    @Query("SELECT n FROM Notice n WHERE n.title = :title ORDER BY n.createdDate DESC")
    List<Notice> findByTitle(@Param("title") String title);

    @Query("SELECT n FROM Notice n ORDER BY n.createdDate DESC LIMIT 5")
    List<Notice> findLast5Notices();

    @Query("SELECT n FROM Notice n ORDER BY n.createdDate DESC")
    List<Notice> findLastNotices(Pageable pageable);

}
