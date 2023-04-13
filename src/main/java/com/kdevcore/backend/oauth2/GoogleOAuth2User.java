package com.kdevcore.backend.oauth2;

import java.util.Map;

public class GoogleOAuth2User extends OAuth2UserInfo {
    public GoogleOAuth2User(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getIdentifier() {
        return String.valueOf(attributes.get("sub"));
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
