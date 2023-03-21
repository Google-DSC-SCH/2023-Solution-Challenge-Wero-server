package com.gdsc.wero.domain.diary.api.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiarySaveReqDto {

    private LocalDate date;
    private String title;
    private String contents;

    @Builder
    public DiarySaveReqDto(LocalDate date, String title, String contents) {
        this.date = date;
        this.title = title;
        this.contents = contents;
    }
}
