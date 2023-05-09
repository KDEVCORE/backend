package com.kdevcore.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdevcore.backend.model.TagEntity;


public interface TagRepository extends JpaRepository<TagEntity, String> {
}
