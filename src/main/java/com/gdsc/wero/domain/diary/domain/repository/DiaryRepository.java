package com.gdsc.wero.domain.diary.domain.repository;

import com.gdsc.wero.domain.diary.domain.Diary;
import com.gdsc.wero.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    // fk 객체 + 필드명으로 조회
    Optional<Diary> findDiaryByUserAndDiaryDate(User user, LocalDate date);

}
