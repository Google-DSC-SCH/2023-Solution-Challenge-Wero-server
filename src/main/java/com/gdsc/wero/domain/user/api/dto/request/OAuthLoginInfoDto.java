package com.gdsc.wero.domain.user.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuthLoginInfoDto {

    private String email;
    private String provider;
}
