package com.kdevcore.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kdevcore.backend.model.UserEntity;
import com.kdevcore.backend.persistence.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity) {
        if(userEntity == null || userEntity.getIdentifier() == null) throw new RuntimeException("Invalid arguments");
        final String identifier = userEntity.getIdentifier();
        if(userRepository.existsByIdentifier(identifier)) {
            log.warn("ID already exists {}", identifier);
            throw new RuntimeException("Username already exists");
        }
        return userRepository.save(userEntity);
    }

    public UserEntity getByCredentials(final String identifier, final String password, final PasswordEncoder encoder) {
        final UserEntity originalMember = userRepository.findByIdentifier(identifier);
        if(originalMember != null && encoder.matches(password, originalMember.getPassword())) return originalMember;
        return null;
    }
}
