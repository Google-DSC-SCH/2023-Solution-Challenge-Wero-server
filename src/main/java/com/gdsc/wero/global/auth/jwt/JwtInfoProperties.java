package com.gdsc.wero.global.auth.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtInfoProperties {
    private String secret;
    private Long expireMin;
    private Long refreshExpireMin;


}

