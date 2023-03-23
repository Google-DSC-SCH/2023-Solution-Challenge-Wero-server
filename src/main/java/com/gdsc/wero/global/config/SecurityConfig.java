package com.gdsc.wero.global.config;

import com.gdsc.wero.global.auth.oauth.CustomOAuth2UserService;
import com.gdsc.wero.global.auth.oauth.handler.OAuth2AuthenticationSuccessHandlerImpl;
import com.gdsc.wero.global.auth.jwt.AuthEntryPointJwt;
import com.gdsc.wero.global.auth.jwt.AuthTokenFilter;
import com.gdsc.wero.global.auth.jwt.JwtExceptionHandlerFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthEntryPointJwt unauthorizedHandler;
    private final AuthTokenFilter authenticationJwtTokenFilter;
    private final JwtExceptionHandlerFilter jwtExceptionHandlerFilter; // jwt exception handler filter!!
    private final CustomOAuth2UserService customOAuth2UserService;

    private final OAuth2AuthenticationSuccessHandlerImpl oAuth2AuthenticationSuccessHandler;
//    private final OAuth2AuthenticationFailureHandlerImpl oAuth2AuthenticationFailureHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/favicon.ico", "/error").permitAll()
                .antMatchers("/test/login", "/api/auth/login-success").permitAll()
                .antMatchers("/login/**", "/swagger-ui/**", "/swagger-resources/**", "/swagger-resources", "/v3/api-docs").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .successHandler(oAuth2AuthenticationSuccessHandler)
//                .failureHandler(oAuth2AuthenticationFailureHandler)
                .userInfoEndpoint()
                .userService(customOAuth2UserService); // 실행순서 : userInfoEndpoint().userService -> successHandler()
        


        http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionHandlerFilter, authenticationJwtTokenFilter.getClass()); // jwt 예외처리 필터

        return http.build();

    }



}
