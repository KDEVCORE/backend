package com.kdevcore.backend.oauth2;

import java.util.Map;

public class GitHubOAuth2User extends OAuth2UserInfo {
    public GitHubOAuth2User(Map<String, Object> attributes) {
        super(attributes);
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
