package com.gdsc.wero.domain.mbti.api.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MbtiReqDto {

    private String mbti;
    private Integer m_score;
    private Integer b_score;
    private Integer t_score;
    private Integer i_score;

    @Builder
    public MbtiReqDto(String mbti, Integer m_score, Integer b_score, Integer t_score, Integer i_score) {
        this.mbti = mbti;
        this.m_score = m_score;
        this.b_score = b_score;
        this.t_score = t_score;
        this.i_score = i_score;
    }
}
