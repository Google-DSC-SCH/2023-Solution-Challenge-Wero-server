package com.gdsc.wero.domain.mbti.api.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gdsc.wero.domain.mbti.domain.Mbti;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MbtiInfoResDto {
    private String mbti;

    @JsonCreator
    public MbtiInfoResDto(Mbti mbti) {
        this.mbti = mbti.getMbti();
    }
}
