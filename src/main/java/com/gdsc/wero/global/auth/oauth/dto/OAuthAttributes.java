package com.gdsc.wero.global.auth.oauth.dto;

import com.gdsc.wero.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private String provider;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture, String provider) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.provider = provider;
    }

    /**
     *
     * userNameAttribute
     * OAuth2.0을 사용해 자동 로그인을 누가 하는지 판단
     * 여기서 registrationId는 OAuth2 로그인을 처리한 서비스 명("google","kakao","naver"..)이 되고,
     * userNameAttributeName은 해당 서비스의 map의 키값이 되는 값이 돱. {google="sub", kakao="id", naver="response"}
     */
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        log.info("================== userNameAttributeName : " + userNameAttributeName + " ==================");

        // Google
        return ofGoogle(userNameAttributeName, attributes);
    }


    /**
     * 구글 생성자, 구글로 로그인할 때 사용
     */
    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){

        log.info("================= Google Login ==================");
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .provider("google")
                .build();
    }

    public User getEntity(){

        return User.createUser(email, name, picture, provider);
    }
}
