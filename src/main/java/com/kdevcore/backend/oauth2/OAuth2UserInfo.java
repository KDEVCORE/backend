package com.kdevcore.backend.oauth2;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public abstract String getIdentifier();
    public abstract String getName();
    public abstract String getEmail();
}
