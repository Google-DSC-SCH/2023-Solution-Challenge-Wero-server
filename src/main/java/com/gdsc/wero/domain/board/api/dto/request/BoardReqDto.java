package com.gdsc.wero.domain.board.api.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardReqDto {
    private String title;
    private String contents;
//    private MultipartFile img;

    @Builder
    public BoardReqDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
//        this.img = img;
    }
}
