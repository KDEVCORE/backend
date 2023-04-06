package com.kdevcore.backend.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RedirectUrlCookieFilter extends OncePerRequestFilter {
    public static final String REDIRECT_URL_PARAM = "redirect-url";
    private static final int MAX_AGE = 180;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().startsWith("/member/authorize")) {
            try {
                log.info("request url {}", request.getRequestURI());
                String redirectUrl = request.getParameter(REDIRECT_URL_PARAM);
                Cookie cookie = new Cookie(REDIRECT_URL_PARAM, redirectUrl);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setMaxAge(MAX_AGE);
                response.addCookie(cookie);
            } catch(Exception e) {
                log.error("Could not set user authentication in security context", e);
                log.info("Unauthorized reqeust");
            }
        }
        filterChain.doFilter(request, response);
    }
}
