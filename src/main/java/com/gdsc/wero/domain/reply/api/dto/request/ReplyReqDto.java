package com.gdsc.wero.domain.reply.api.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyReqDto {
    private String contents;

    @JsonCreator
    public ReplyReqDto(String contents) {
        this.contents = contents;
    }
}
