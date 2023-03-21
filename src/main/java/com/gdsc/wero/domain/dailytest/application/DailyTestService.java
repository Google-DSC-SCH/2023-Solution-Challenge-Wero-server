package com.gdsc.wero.domain.dailytest.application;

import com.gdsc.wero.domain.dailytest.api.dto.request.DailyTestSaveReqDto;
import com.gdsc.wero.domain.dailytest.api.dto.response.DailyTestInfoResDto;
import com.gdsc.wero.domain.dailytest.api.dto.response.DailyTestResListDto;
import com.gdsc.wero.domain.dailytest.domain.DailyTest;
import com.gdsc.wero.domain.dailytest.domain.repository.DailyTestRepository;
import com.gdsc.wero.domain.user.domain.User;
import com.gdsc.wero.domain.user.domain.repository.UserRepository;
import com.gdsc.wero.global.exception.errortype.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DailyTestService {

    private final DailyTestRepository dailyTestRepository;
    private final UserRepository userRepository;

    /**
     * Daily test 7일치 조회
     */
    public DailyTestResListDto getDailyTestByWeek(Pageable pageable, String email, String provider){
        // 유저 조회
        User user = userRepository.findUserByEmailAndProvider(email, provider).orElseThrow(() -> new UserNotFoundException("User does not exist"));

        List<DailyTestInfoResDto> collect = dailyTestRepository.findDailyTestByUserOrderByDailyDateDesc(user, pageable)
                .stream().
                map(DailyTestInfoResDto::new)
                .collect(Collectors.toList());

        return new DailyTestResListDto(collect);
    }


    /**
     * Daily test 저장
     */
    public void saveDailyTest(DailyTestSaveReqDto dailyTestSaveReqDto, String email, String provider) {

        // 유저 조회
        User user = userRepository.findUserByEmailAndProvider(email, provider).orElseThrow(() -> new UserNotFoundException("User does not exist"));


        DailyTest dailyTest = DailyTest.createDailyTest(dailyTestSaveReqDto, user);

        dailyTestRepository.save(dailyTest);

        log.info("=============== DAILY TEST BEING SAVED ==============");

    }
}
