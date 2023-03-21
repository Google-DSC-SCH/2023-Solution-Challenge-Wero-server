package com.gdsc.wero.domain.user.application;

import com.gdsc.wero.domain.user.domain.User;
import com.gdsc.wero.domain.user.domain.repository.UserRepository;
import com.gdsc.wero.domain.user.api.dto.request.UserInfoReqDto;
import com.gdsc.wero.global.exception.errortype.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * User info 저장 혹운 수정
     */
    public void saveOrUpdateUserInfo(UserInfoReqDto userInfoReqDto, String email, String provider){

        // 유저 조회
        User user = userRepository.findUserByEmailAndProvider(email, provider).orElseThrow(() -> new UserNotFoundException("유저가 존재하지 않습니다."));

        user.updateUserInfo(userInfoReqDto);

        log.info("================== USER INFO IS BEING SAVED OR UPDATED ===================");

    }

}
