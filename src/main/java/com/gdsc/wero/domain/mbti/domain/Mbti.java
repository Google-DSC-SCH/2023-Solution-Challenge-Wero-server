package com.gdsc.wero.domain.mbti.domain;

import com.gdsc.wero.domain.common.BaseTimeEntity;
import com.gdsc.wero.domain.mbti.api.dto.request.MbtiReqDto;
import com.gdsc.wero.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Mbti extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mbti_id")
    private Long mbtiId;
    private String mbti;
    private Integer m_score;
    private Integer b_score;
    private Integer t_score;
    private Integer i_score;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private User user;

    @Builder
    private Mbti(String mbti, Integer m_score, Integer b_score, Integer t_score, Integer i_score, User user) {
        this.mbti = mbti;
        this.m_score = m_score;
        this.b_score = b_score;
        this.t_score = t_score;
        this.i_score = i_score;
        this.user = user;
    }


    /**
     * Mbti 생성 메서드
     */
    public static Mbti createMbti(MbtiReqDto mbtiReqDto, User user) {
        return Mbti.builder()
                .mbti(mbtiReqDto.getMbti())
                .m_score(mbtiReqDto.getM_score())
                .b_score(mbtiReqDto.getB_score())
                .t_score(mbtiReqDto.getT_score())
                .i_score(mbtiReqDto.getI_score())
                .user(user)
                .build();
    }

    /**
     * Mbti 수정 메서드
     */
    public void updateMbti(MbtiReqDto mbtiReqDto){
        this.mbti = mbtiReqDto.getMbti();
        this.m_score = mbtiReqDto.getM_score();
        this.b_score = mbtiReqDto.getB_score();
        this.t_score = mbtiReqDto.getT_score();
        this.i_score = mbtiReqDto.getI_score();

    }


}
