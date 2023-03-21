package com.gdsc.wero.domain.mbti.domain.repository;

import com.gdsc.wero.domain.mbti.domain.Mbti;
import com.gdsc.wero.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MbtiRepository extends JpaRepository<Mbti, Long> {

    // fk 객체로 바로 조회
    Optional<Mbti> findMbtiByUser(User user);

    // fk키로 조회
//    Optional<Mbti> findByUser_Email(String Email);
}


