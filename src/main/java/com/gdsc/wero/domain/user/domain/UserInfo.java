package com.gdsc.wero.domain.user.domain;

import com.gdsc.wero.domain.user.api.dto.request.UserInfoReqDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserInfo {

    private String nickname;
    private Integer age;
    private String sex;


    public UserInfo(UserInfoReqDto userInfoReqDto) {
        this.nickname = userInfoReqDto.getNickname();
        this.age = userInfoReqDto.getAge();
        this.sex = userInfoReqDto.getSex();
    }


}
