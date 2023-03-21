package com.gdsc.wero.domain.dailytest.api.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyTestSaveReqDto {
    private Integer dailyScore;
    private LocalDate dailyDate;
    private String dailyEmotion;

    @Builder
    public DailyTestSaveReqDto(Integer dailyScore, LocalDate dailyDate, String dailyEmotion) {
        this.dailyScore = dailyScore;
        this.dailyDate = dailyDate;
        this.dailyEmotion = dailyEmotion;
    }
}
