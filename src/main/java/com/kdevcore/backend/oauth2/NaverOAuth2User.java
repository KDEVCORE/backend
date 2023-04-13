package com.kdevcore.backend.oauth2;

import java.util.Map;

public class NaverOAuth2User extends OAuth2UserInfo {
    public NaverOAuth2User(Map<String, Object> attributes) {
        super((Map<String, Object>) attributes.get("response"));
    }

    @Override
    public String getIdentifier() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getName() {
        return String.valueOf(attributes.get("name"));
    }

    @Override
    public String getEmail() {
        return String.valueOf(attributes.get("email"));
    }
}
