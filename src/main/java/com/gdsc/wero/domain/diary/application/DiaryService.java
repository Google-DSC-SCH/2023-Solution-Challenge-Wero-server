package com.gdsc.wero.domain.diary.application;

import com.gdsc.wero.domain.diary.api.dto.request.DiaryGetDateReqDto;
import com.gdsc.wero.domain.diary.api.dto.request.DiarySaveReqDto;
import com.gdsc.wero.domain.diary.api.dto.response.DiaryInfoResDto;
import com.gdsc.wero.domain.diary.domain.Diary;
import com.gdsc.wero.domain.diary.domain.repository.DiaryRepository;
import com.gdsc.wero.domain.diary.exception.DiaryNotExistException;
import com.gdsc.wero.domain.user.domain.User;
import com.gdsc.wero.domain.user.domain.repository.UserRepository;
import com.gdsc.wero.global.exception.errortype.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    /**
     * 날짜별 Diary 조회
     */
    public DiaryInfoResDto getDiary(DiaryGetDateReqDto diaryGetDateReqDto, String email, String provider){
        // 유저 조회
        User user = userRepository.findUserByEmailAndProvider(email, provider).orElseThrow(() -> new UserNotFoundException("User does not exist | email : " + email));

        Diary diary = diaryRepository.findDiaryByUserAndDiaryDate(user, LocalDate.parse(diaryGetDateReqDto.getDate())).orElseThrow(() -> new DiaryNotExistException("This diary does not exist"));

        log.info("================ DIARY IS BEING SEARCHED================");
        return new DiaryInfoResDto(diary);

    }

    /**
     * Diary 저장
     */
    public void saveDiary(DiarySaveReqDto diarySaveReqDto, String email, String provider) {

        // 유저 조회
        User user = userRepository.findUserByEmailAndProvider(email, provider).orElseThrow(() -> new UserNotFoundException("User does not exist | email : " + email));

        Diary diary = Diary.createDiary(diarySaveReqDto, user);

        diaryRepository.save(diary);

        log.info("================== DIARY IS BEING SAVED ===================");
    }


}
