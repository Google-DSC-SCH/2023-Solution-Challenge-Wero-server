package com.gdsc.wero.domain.dailytest.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gdsc.wero.domain.dailytest.domain.DailyTest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyTestInfoResDto {
    private Integer dailyScore;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul") // 배열로 나오길래 format 지정해줌
    private LocalDate dailyDate;
    private String dailyEmotion;


    /**
     * Entity -> Dto 변환 메서드
     */
    public DailyTestInfoResDto(DailyTest entity) {
        this.dailyScore = entity.getDailyScore();
        this.dailyDate = entity.getDailyDate();
        this.dailyEmotion = entity.getDailyEmotion();
    }
}
