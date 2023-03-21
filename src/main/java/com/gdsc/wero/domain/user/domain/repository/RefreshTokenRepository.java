package com.gdsc.wero.domain.user.domain.repository;

import com.gdsc.wero.domain.user.domain.RefreshToken;
import com.gdsc.wero.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);

    @Modifying
    int deleteByUser(User user);

    Boolean existsByToken(String token);


}
