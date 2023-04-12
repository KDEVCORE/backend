package com.kdevcore.backend.oauth2;

import java.util.Map;

public class GitHubOAuth2User extends OAuth2UserInfo {
    public GitHubOAuth2User(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getIdentifier() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        return attributes.get("name").toString();
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }
}
