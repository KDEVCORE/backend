package com.kdevcore.backend.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdevcore.backend.model.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, String> {
    List<PostEntity> findByContentContaining(String content);
}
