package com.gdsc.wero.domain.dailytest.domain.repository;

import com.gdsc.wero.domain.dailytest.domain.DailyTest;
import com.gdsc.wero.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyTestRepository extends JpaRepository<DailyTest, Long> {

    /**
     * DailyDate(LocalDate) 기준 DESC로 7개씩 페이징 처리 후 조회
     */
    Page<DailyTest> findDailyTestByUserOrderByDailyDateDesc(User user, Pageable pageable);
}
