package com.kdevcore.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.filter.CorsFilter;

import com.kdevcore.backend.oauth2.OAuth2SuccessHandler;
import com.kdevcore.backend.security.JwtAuthenticationFilter;
import com.kdevcore.backend.security.RedirectUriCookieFilter;
import com.kdevcore.backend.service.CustomOAuth2UserService;

// Spring Seucrity 버전 업그레이드 이슈, WebSecurityConfigurerAdapter 대체, 기존의 @Override 방식에서 @Bean 객체 등록 방식으로 변경
@Configuration
public class WebSecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    private OAuth2SuccessHandler oAuth2SuccessHandler;
    @Autowired
    private RedirectUriCookieFilter redirectUriCookieFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors((cors) -> cors.and())
            .csrf((csrf) -> csrf.disable())
            .httpBasic((basic) -> basic.disable())
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((authorize) -> authorize.antMatchers("/", "/health", "/member/**", "/oauth2/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**", "/webjars/**").permitAll().anyRequest().authenticated())
            .oauth2Login((oauth) -> oauth.redirectionEndpoint().baseUri("/oauth2/callback/*").and()
                                    .authorizationEndpoint().baseUri("/oauth2/authorization").and()
                                    .userInfoEndpoint().userService(customOAuth2UserService).and()
                                    .successHandler(oAuth2SuccessHandler))
            .exceptionHandling((exception) -> exception.authenticationEntryPoint(new Http403ForbiddenEntryPoint()));
        http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
        http.addFilterBefore(redirectUriCookieFilter, OAuth2AuthorizationRequestRedirectFilter.class);
        return http.build();
    }
}
