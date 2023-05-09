package com.kdevcore.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdevcore.backend.dto.PostDTO;
import com.kdevcore.backend.dto.ResponseDTO;
import com.kdevcore.backend.model.PostEntity;
import com.kdevcore.backend.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {
        try {
            Optional<PostEntity> postEntityOne = postService.create(PostDTO.toEntity(postDTO));
            List<PostDTO> postList = postEntityOne.stream().map(PostDTO::new).collect(Collectors.toList());
            ResponseDTO<PostDTO> response = ResponseDTO.<PostDTO>builder().data(postList).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO<String> response = ResponseDTO.<String>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrievePost(@RequestBody PostDTO postDTO) {
        if(postDTO.getUuid() == null) {
            List<PostEntity> postEntityList = postService.retrieve();
            List<PostDTO> postList = postEntityList.stream().map(PostDTO::new).collect(Collectors.toList());
            ResponseDTO<PostDTO> response = ResponseDTO.<PostDTO>builder().data(postList).build();
            return ResponseEntity.ok().body(response);
        } else {
            Optional<PostEntity> postEntityOne = postService.retrieve(postDTO.getUuid());
            List<PostDTO> postList = postEntityOne.stream().map(PostDTO::new).collect(Collectors.toList());
            ResponseDTO<PostDTO> response = ResponseDTO.<PostDTO>builder().data(postList).build();
            return ResponseEntity.ok().body(response);
        }
    }
    
    @PutMapping
    public ResponseEntity<?> updatePost(@RequestBody PostDTO postDTO) {
        PostEntity post = PostDTO.toEntity(postDTO);
        Optional<PostEntity> postEntityOne = postService.update(post);
        List<PostDTO> postList = postEntityOne.stream().map(PostDTO::new).collect(Collectors.toList());
        ResponseDTO<PostDTO> response = ResponseDTO.<PostDTO>builder().data(postList).build();
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deletePost(@RequestBody PostDTO postDTO) {
        try{
            postService.delete(postDTO.getUuid());
            List<PostEntity> postEntityList = postService.retrieve();
            List<PostDTO> postList = postEntityList.stream().map(PostDTO::new).collect(Collectors.toList());
            ResponseDTO<PostDTO> response = ResponseDTO.<PostDTO>builder().data(postList).build();
            return ResponseEntity.ok().body(response);
        } catch(Exception e) {
            ResponseDTO<String> response = ResponseDTO.<String>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
