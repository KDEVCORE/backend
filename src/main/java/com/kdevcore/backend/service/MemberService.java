package com.kdevcore.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kdevcore.backend.model.MemberEntity;
import com.kdevcore.backend.persistence.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public MemberEntity create(final MemberEntity userEntity) {
        if(userEntity == null || userEntity.getUsername() == null) throw new RuntimeException("Invalid arguments");
        final String username = userEntity.getUsername();
        if(memberRepository.existsByUsername(username)) {
            log.warn("Username already exists {}", username);
            throw new RuntimeException("Username already exists");
        }
        return memberRepository.save(userEntity);
    }

    public MemberEntity getByCredentials(final String username, final String password, final PasswordEncoder encoder) {
        final MemberEntity originalMember = memberRepository.findByUsername(username);
        if(originalMember != null && encoder.matches(password, originalMember.getPassword())) return originalMember;
        return null;
    }
}
