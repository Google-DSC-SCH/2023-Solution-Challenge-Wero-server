package com.gdsc.wero.domain.mbti.application;

import com.gdsc.wero.domain.mbti.api.dto.request.MbtiReqDto;
import com.gdsc.wero.domain.mbti.api.dto.response.MbtiInfoResDto;
import com.gdsc.wero.domain.mbti.domain.Mbti;
import com.gdsc.wero.domain.mbti.domain.repository.MbtiRepository;
import com.gdsc.wero.domain.mbti.exception.MbtiNotExistException;
import com.gdsc.wero.domain.user.domain.User;
import com.gdsc.wero.domain.user.domain.repository.UserRepository;
import com.gdsc.wero.global.exception.errortype.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class MbtiService {

    private final MbtiRepository mbtiRepository;
    private final UserRepository userRepository;


    public MbtiInfoResDto getMbti(String email, String provider) {

        // 유저 조회
        User user = userRepository.findUserByEmailAndProvider(email, provider).orElseThrow(() -> new UserNotFoundException("User does not exist | email : " + email));

        Mbti mbti = mbtiRepository.findMbtiByUser(user).orElseThrow(() -> new MbtiNotExistException("User's mbti does not exist | email : " + email));

        return new MbtiInfoResDto(mbti);

    }

    /**
     * mbti 등록 혹은 수정
     */
    public void saveOrUpdateMbti(MbtiReqDto mbtiReqDto, String email, String provider) {

        // 유저 조회
        User user = userRepository.findUserByEmailAndProvider(email, provider).orElseThrow(() -> new UserNotFoundException("User does not exist | email : " + email));

        Optional<Mbti> getMbti = mbtiRepository.findMbtiByUser(user);

        if (getMbti.isEmpty()) {
            Mbti mbti = Mbti.createMbti(mbtiReqDto, user);

            mbtiRepository.save(mbti);

            log.info("=============== MBTI TEST IS BEING SAVED ==============");

        }
        else{
            getMbti.get().updateMbti(mbtiReqDto);

            log.info("=============== MBTI TEST IS BEING UPDATED ==============");

        }

    }

}
