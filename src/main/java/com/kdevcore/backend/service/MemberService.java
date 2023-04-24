package com.kdevcore.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kdevcore.backend.model.UserEntity;
import com.kdevcore.backend.persistence.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity) {
        if(userEntity == null || userEntity.getIdentifier().isBlank()) throw new RuntimeException("Invalid arguments");
        final String identifier = userEntity.getIdentifier();
        if(userRepository.existsByIdentifier(identifier)) {
            log.warn("ID already exists {}", identifier);
            throw new RuntimeException("Username already exists");
        }
        return userRepository.save(userEntity);
    }

    public UserEntity retrieve(final String userIdentifier) {
        return userRepository.findByIdentifier(userIdentifier);
    }

    @Transactional
    public UserEntity update(final UserEntity userEntity) {
        final UserEntity original = userRepository.findByIdentifier(userEntity.getIdentifier());
        if(isValidIdentifier(original.getIdentifier())) {
            userRepository.save(
                UserEntity.builder()
                        .uuid(original.getUuid())
                        .identifier(original.getIdentifier())
                        .password(userEntity.getPassword())
                        .email(userEntity.getEmail())
                        .name(userEntity.getName())
                        .provider(original.getProvider())
                        .role(original.getRole())
                        .build()
            );
        }
        return retrieve(userEntity.getIdentifier());
    }

    @Transactional
    public Boolean delete(final UserEntity userEntity) {
        try {
            userRepository.delete(userEntity);
        } catch(Exception e) {
            String msg = "error deleting entity: " + userEntity.getUuid();
            log.error(msg, e);
            throw new RuntimeException(msg);
        }
        return true;
    }

    public UserEntity getByCredentials(final String identifier, final String password, final PasswordEncoder encoder) {
        final UserEntity originalMember = userRepository.findByIdentifier(identifier);
        if(originalMember != null && encoder.matches(password, originalMember.getPassword())) return originalMember;
        return null;
    }

    public Boolean isValidIdentifier(final String identifier) {
        return userRepository.existsByIdentifier(identifier);
    }
}
