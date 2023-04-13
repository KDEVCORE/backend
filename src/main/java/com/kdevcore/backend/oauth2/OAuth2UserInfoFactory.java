package com.kdevcore.backend.oauth2;

import java.util.Map;

import com.kdevcore.backend.enums.Provider;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(Provider provider, Map<String, Object> attributes) {
        switch(provider) {
            case GOOGLE: return new GoogleOAuth2User(attributes);
            case KAKAO: return new KakaoOAuth2User(attributes);
            case NAVER: return new NaverOAuth2User(attributes);
            case GITHUB: return new GitHubOAuth2User(attributes);
            default: throw new IllegalArgumentException("Invalid provider type - This platform is not supported.");
        }
    }
}
