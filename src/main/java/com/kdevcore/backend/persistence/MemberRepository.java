package com.kdevcore.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdevcore.backend.model.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    MemberEntity findByUsername(String username);
    Boolean existsByUsername(String username);
    MemberEntity findByUsernameAndPassword(String username, String password);
}
