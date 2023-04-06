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

import com.kdevcore.backend.security.JwtAuthenticationFilter;
import com.kdevcore.backend.security.OAuthSuccessHandler;
import com.kdevcore.backend.security.OAuthUserServiceImpl;
import com.kdevcore.backend.security.RedirectUriCookieFilter;

// Spring Seucrity 버전 업그레이드 이슈, WebSecurityConfigurerAdapter 대체, 기존의 @Override 방식에서 @Bean 객체 등록 방식으로 변경
@Configuration
public class SecurityConfiguration {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private OAuthUserServiceImpl oAuthUserServiceImpl;
    @Autowired
    private OAuthSuccessHandler oAuthSuccessHandler;
    @Autowired
    private RedirectUriCookieFilter redirectUriCookieFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors((cors) -> cors.and())
            .csrf((csrf) -> csrf.disable())
            .httpBasic((basic) -> basic.disable())
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and())
            .authorizeHttpRequests((authorize) -> authorize.antMatchers("/", "/member/**", "/oauth2/**").permitAll().anyRequest().authenticated().and())
            .oauth2Login((oauth) -> oauth.redirectionEndpoint().baseUri("/oauth2/callback/*").and()
                                        .authorizationEndpoint().baseUri("/member/authorize").and()
                                        .userInfoEndpoint().userService(oAuthUserServiceImpl).and()
                                        .successHandler(oAuthSuccessHandler).and())
            .exceptionHandling((exception) -> exception.authenticationEntryPoint(new Http403ForbiddenEntryPoint()));
        http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
        http.addFilterBefore(redirectUriCookieFilter, OAuth2AuthorizationRequestRedirectFilter.class);
        return http.build();
    }
}
