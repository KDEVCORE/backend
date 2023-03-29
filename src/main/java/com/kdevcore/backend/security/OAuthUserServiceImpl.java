package com.kdevcore.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdevcore.backend.model.UserEntity;
import com.kdevcore.backend.persistence.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OAuthUserServiceImpl extends DefaultOAuth2UserService {
    @Autowired
    private UserRepository userRepository;
    
    public OAuthUserServiceImpl() {
        super();
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            log.info("OAuth2User attributes() {}", new ObjectMapper().writeValueAsString(oAuth2User.getAttributes())); // for test
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
        final String username = oAuth2User.getAttributes().get("login").toString();
        final String authProvider = userRequest.getClientRegistration().getClientName();
        UserEntity userEntity = null;
        if(!userRepository.existsByUsername(username)) {
            userEntity = UserEntity.builder().username(username).authProvider(authProvider).build();
            userEntity = userRepository.save(userEntity);
        } else {
            userEntity = userRepository.findByUsername(username);
        }
        log.info("Successfully pulled user info: username {}, authProvider: {}", username, authProvider);
        return new ApplicationOAuth2User(userEntity.getId(), oAuth2User.getAttributes());
    }
}
