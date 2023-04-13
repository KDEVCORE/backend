package com.kdevcore.backend.oauth2;

import java.util.Map;

public class KakaoOAuth2User extends OAuth2UserInfo {
    private String id;
    public KakaoOAuth2User(Map<String, Object> attributes) {
        super((Map<String, Object>) attributes.get("kakao_account"));
        this.id = String.valueOf(attributes.get("id"));
    }

    @Override
    public String getIdentifier() {
        return this.id;
    }

    @Override
    public String getName() {
        return String.valueOf(((Map<String, Object>) attributes.get("profile")).get("nickname"));
    }

    @Override
    public String getEmail() {
        return String.valueOf(attributes.get("email"));
    }
}
