package com.gdsc.wero.domain.reply.api.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyResListDto {
    private List<ReplyInfoResDto> reflyInfoList;

    @JsonCreator
    public ReplyResListDto(List<ReplyInfoResDto> replyInfoResDto) {
        this.reflyInfoList = replyInfoResDto;
    }
}
