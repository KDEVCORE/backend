package com.kdevcore.backend.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdevcore.backend.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
    Boolean existsByIdentifier(String identifier);
    UserEntity findByIdentifier(String identifier);
    UserEntity findByIdentifierAndPassword(String identifier, String password);
}
