package com.gdsc.wero.domain.diary.api.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryGetDateReqDto {

    private String date;

    @JsonCreator
    public DiaryGetDateReqDto(String date) {
        this.date = date;
    }
}
