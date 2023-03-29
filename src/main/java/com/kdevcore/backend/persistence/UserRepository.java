package com.kdevcore.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdevcore.backend.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUsername(String username);
    Boolean existsByUsername(String username);
    UserEntity findByUsernameAndPassword(String username, String password);
}
