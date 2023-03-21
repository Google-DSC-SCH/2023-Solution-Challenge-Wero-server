package com.gdsc.wero.global.auth.oauth.handler;

import com.gdsc.wero.global.auth.jwt.JwtUtils;
import com.gdsc.wero.domain.user.domain.RefreshToken;
import com.gdsc.wero.domain.user.application.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String[] path = request.getRequestURI().split("/");
        String provider = path[path.length-1];

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");


        log.info("=========== " + provider + " Social Login Success" + " ===============");
        log.info("=========== Email : " + email + " ===============");


        // refreshToken db 생성 및 저장
        RefreshToken refreshToken =  refreshTokenService.createRefreshToken(email, provider);
        log.info("=========== Generated Refresh Token : " + refreshToken.getToken() + " ===============");

        // access-token(jwt) 생성
        String accessToken = jwtUtils.generateTokenFromEmailAndProvider(email, provider);
        log.info("=========== Generated Access Token : " + accessToken + " ===============");


        String uri = UriComponentsBuilder.fromUriString("http://localhost:9999/api/auth/login-success")
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken.getToken())
                .build().toUriString();


        response.sendRedirect(uri);




    }
}
