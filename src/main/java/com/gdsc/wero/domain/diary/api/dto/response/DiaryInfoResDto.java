package com.gdsc.wero.domain.diary.api.dto.response;

import com.gdsc.wero.domain.diary.domain.Diary;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryInfoResDto {
    private String title;
    private String contents;


    public DiaryInfoResDto(Diary diary){
        this.title = diary.getTitle();
        this.contents = diary.getContents();
    }

}
