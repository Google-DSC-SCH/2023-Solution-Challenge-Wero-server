package com.gdsc.wero.global.auth.oauth.handler;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "successhandler")
@Getter
@Setter
public class Oauth2InfoProperties {
    private String url;
}
