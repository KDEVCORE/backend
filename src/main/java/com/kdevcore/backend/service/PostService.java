package com.kdevcore.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kdevcore.backend.model.PostEntity;
import com.kdevcore.backend.persistence.PostRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Optional<PostEntity> create(final PostEntity postEntity) {
        return retrieve(postRepository.save(postEntity).getUuid());
    }

    public List<PostEntity> retrieve() {
        return postRepository.findAll();
    }

    public Optional<PostEntity> retrieve(final String uuid) {
        return postRepository.findById(uuid);
    }

    public Optional<PostEntity> update(final PostEntity postEntity) {
        Optional<PostEntity> original = postRepository.findById(postEntity.getUuid());
        if(original.isPresent()) {
            postRepository.save(
                PostEntity.builder()
                    .uuid(postEntity.getUuid())
                    .writer(postEntity.getWriter())
                    .headline(postEntity.getHeadline())
                    .content(postEntity.getContent())
                    .views(postEntity.getViews())
                    .build());
        }
        return retrieve(postEntity.getUuid());
    }

    public void delete(final String uuid) {
        Optional<PostEntity> original = postRepository.findById(uuid);
        if(original.isPresent()) {
            try {
                postRepository.deleteById(uuid);
            } catch(Exception e) {
                throw new RuntimeException("Error deleting: " + uuid);
            }
        } else throw new RuntimeException("Already deleted or does not exist");
    }
}
