package com.gdsc.wero.global.resolver;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoFromHeaderDto {
    private String email;
    private String provider;

    public UserInfoFromHeaderDto(String email, String provider) {
        this.email = email;
        this.provider = provider;
    }
}
