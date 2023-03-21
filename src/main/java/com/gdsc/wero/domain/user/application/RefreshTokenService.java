package com.gdsc.wero.domain.user.application;

import com.gdsc.wero.domain.user.domain.RefreshToken;
import com.gdsc.wero.domain.user.domain.User;
import com.gdsc.wero.global.auth.jwt.exception.errortype.RefreshTokenExpiredException;
import com.gdsc.wero.domain.user.domain.repository.RefreshTokenRepository;
import com.gdsc.wero.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${jwt.refreshExpireMin}")
    private Long refreshTokenMin;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    /**
     * 리프레쉬 토큰 토큰 조회
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }


    /**
     * 리프레쉬 토큰 생성 및 저장
     */
    @Transactional
    public RefreshToken createRefreshToken(String email, String provider) {

        User user = userRepository.findUserByEmailAndProvider(email, provider).orElseThrow(() -> new UsernameNotFoundException("User does not exist | email: " + email));

        RefreshToken refreshToken = RefreshToken.createRefreshToken(UUID.randomUUID().toString(), Instant.now().plusMillis(refreshTokenMin), user);

        Optional<RefreshToken> byUser = refreshTokenRepository.findByUser(user);

        // db에 있다면 삭제
        if(byUser.isPresent()){
            refreshTokenRepository.deleteByUser(user);
        }

        // db 저장
        RefreshToken saveRefreshToken = refreshTokenRepository.save(refreshToken);

        log.info("==================== REFRESH TOKEN IS BEING SAVED ======================");

        return saveRefreshToken;
    }


    /**
     * 만료시간 검증
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);

            log.error("==================== REFRESH TOKEN HAS BEEN EXPIRED ====================");

            throw new RefreshTokenExpiredException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }


    /**
     * 리프레쉬 토큰 삭제
     */
    @Transactional
    public int deleteByUserId(RefreshToken refreshToken) {

        return refreshTokenRepository.deleteByUser(refreshToken.getUser());
    }


    public Optional<RefreshToken> findByEmailAndProvider(String email, String provider) {
        Optional<User> user = userRepository.findUserByEmailAndProvider(email, provider);

        return refreshTokenRepository.findByUser(user.get());
    }
}
