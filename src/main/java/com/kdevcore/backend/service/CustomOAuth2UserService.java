package com.kdevcore.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.kdevcore.backend.enums.Provider;
import com.kdevcore.backend.enums.Role;
import com.kdevcore.backend.model.UserEntity;
import com.kdevcore.backend.oauth2.OAuth2UserInfo;
import com.kdevcore.backend.oauth2.OAuth2UserInfoFactory;
import com.kdevcore.backend.oauth2.UserPrincipal;
import com.kdevcore.backend.persistence.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);
        return processOAuth2User(oAuth2UserRequest, oAuth2User);
    }

    protected OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        Provider provider = Provider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());
        log.info("Authentication Provioder: " + provider + ", OAuth2User: " + oAuth2User.toString());
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, oAuth2User.getAttributes());

        if(!StringUtils.hasText(oAuth2UserInfo.getEmail())) throw new RuntimeException("Email not found from OAuth2 provider");

        UserEntity user = userRepository.findByIdentifier(oAuth2UserInfo.getIdentifier());
        user = (user != null) ? updateUser(user, oAuth2UserInfo) : registerUser(provider, oAuth2UserInfo); // already exists or new user
        return UserPrincipal.create(user, oAuth2UserInfo.getAttributes());
    }

    private UserEntity registerUser(Provider provider, OAuth2UserInfo oaAuth2UserInfo) {
        return userRepository.save(UserEntity.builder()
                                             .email(oaAuth2UserInfo.getEmail())
                                             .name(oaAuth2UserInfo.getName())
                                             .identifier(oaAuth2UserInfo.getIdentifier())
                                             .provider(provider)
                                             .role(Role.ROLE_PARTNER)
                                             .build());
    }

    private UserEntity updateUser(UserEntity user, OAuth2UserInfo oAuth2UserInfo) {
        return userRepository.save(user.update(oAuth2UserInfo));
    }
}
