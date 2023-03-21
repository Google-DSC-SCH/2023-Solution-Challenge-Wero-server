package com.gdsc.wero.domain.dailytest.api.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyTestResListDto {
    private List<DailyTestInfoResDto> dailyTestList;

    @JsonCreator
    public DailyTestResListDto(List<DailyTestInfoResDto> dailyTest) {
        this.dailyTestList = dailyTest;
    }
}
