package com.gdsc.wero.domain.user.api.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoReqDto {

    private String nickname;
    private int age;
    private String sex;

    @Builder
    public UserInfoReqDto(String nickname, int age, String sex) {
        this.nickname = nickname;
        this.age = age;
        this.sex = sex;
    }
}
