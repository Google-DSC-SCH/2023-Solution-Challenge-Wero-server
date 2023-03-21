package com.gdsc.wero.domain.diary.domain;

import com.gdsc.wero.domain.common.BaseTimeEntity;
import com.gdsc.wero.domain.diary.api.dto.request.DiarySaveReqDto;
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
public class Diary extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long diaryId;
    private String title;
    private String contents;
    private LocalDate diaryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private User user;

    @Builder
    private Diary(String title, String contents, LocalDate diaryDate, User user) {
        this.title = title;
        this.contents = contents;
        this.diaryDate = diaryDate;
        this.user = user;
    }

    /**
     * Diary 생성 메서드
     */
    public static Diary createDiary(DiarySaveReqDto diarySaveReqDto, User user){
        return Diary.builder()
                .title(diarySaveReqDto.getTitle())
                .contents(diarySaveReqDto.getContents())
                .diaryDate(diarySaveReqDto.getDate())
                .user(user)
                .build();

    }
}
