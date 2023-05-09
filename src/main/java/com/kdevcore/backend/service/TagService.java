package com.kdevcore.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kdevcore.backend.model.TagEntity;
import com.kdevcore.backend.persistence.TagRepository;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public void create(TagEntity tagEntity) {
        tagRepository.save(tagEntity);
    }

    public Optional<TagEntity> retrieve(String name) {
        return tagRepository.findById(name);
    }

    public void update(TagEntity tagEntity) {
        Optional<TagEntity> original = tagRepository.findById(tagEntity.getName());
        if(original.isPresent()) {
            tagRepository.save(
                TagEntity.builder()
                    .uuid(tagEntity.getUuid())
                    .name(tagEntity.getName())
                    .count(tagEntity.getCount()+1)
                    .build());
        }
    }

    public void delete(final String uuid) {
        Optional<TagEntity> original = tagRepository.findById(uuid);
        if(original.isPresent()) {
            try {
                tagRepository.deleteById(uuid);
            } catch(Exception e) {
                throw new RuntimeException("Error deleting: " + uuid);
            }
        } else throw new RuntimeException("Already deleted or does not exist");
    }
}
