package com.kdevcore.backend.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.kdevcore.backend.model.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY = "dGhpcyBwcm9qZWN0IGlzIGZvciB3ZWIgZGV2ZWxvcG1lbnQgcmVoZWFyc2Fscy4=";

    public String create(UserEntity userEntity) {
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        return Jwts.builder() // JWT 생성
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // header에 들어갈 내용 및 서명을 하기 위한 SECRET_KEY
                    .setSubject(userEntity.getId()).setIssuer("kdevcore").setIssuedAt(new Date()).setExpiration(expiryDate) // payload에 들어갈 내용
                    .compact();
    }

    public String create(final Authentication authentication) {
        ApplicationOAuth2User userPrincipal = (ApplicationOAuth2User) authentication.getPrincipal();
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        return Jwts.builder() // JWT 생성 (social sign-in)
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // header에 들어갈 내용 및 서명을 하기 위한 SECRET_KEY
                    .setSubject(userPrincipal.getName()).setIssuer("kdevcore").setIssuedAt(new Date()).setExpiration(expiryDate) // payload에 들어갈 내용
                    .compact();
    }

    public String validateAndGetUserId(String token) {
        log.info("User ID validate");
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
