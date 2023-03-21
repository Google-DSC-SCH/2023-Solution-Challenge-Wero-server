package com.gdsc.wero.domain.dailytest.domain;

import com.gdsc.wero.domain.common.BaseTimeEntity;
import com.gdsc.wero.domain.dailytest.api.dto.request.DailyTestSaveReqDto;
import com.gdsc.wero.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DailyTest extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_test_id")
    private Long dailyTestId;
    private Integer dailyScore;
    private LocalDate dailyDate;
    private String dailyEmotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private User user;

    @Builder
    private DailyTest(Integer dailyScore, LocalDate dailyDate, String dailyEmotion, User user) {
        this.dailyScore = dailyScore;
        this.dailyDate = dailyDate;
        this.dailyEmotion = dailyEmotion;
        this.user = user;
    }

    public static DailyTest createDailyTest(DailyTestSaveReqDto dailyTestSaveReqDto, User user){
        return DailyTest.builder()
                .dailyScore(dailyTestSaveReqDto.getDailyScore())
                .dailyDate(dailyTestSaveReqDto.getDailyDate())
                .dailyEmotion(dailyTestSaveReqDto.getDailyEmotion())
                .user(user)
                .build();
    }
}
