package com.kdevcore.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdevcore.backend.dto.UserDTO;
import com.kdevcore.backend.model.UserEntity;
import com.kdevcore.backend.security.TokenProvider;
import com.kdevcore.backend.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("auth")
public class MemberController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Bean으로 작성해도 됨

    @PostMapping("signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            if(userDTO == null || userDTO.getPassword() == null) throw new RuntimeException("Invalid Password value");
            UserEntity user = UserEntity.builder().username(userDTO.getUsername()).password(passwordEncoder.encode(userDTO.getPassword())).build();
            UserEntity registeredUser = userService.create(user);
            UserDTO responseUserDTO = UserDTO.builder().id(registeredUser.getId()).username(registeredUser.getUsername()).build();
            return ResponseEntity.ok().body(responseUserDTO);
        } catch(Exception e) {
            String msg = "Member registration failed: " + e.getMessage();
            log.error(msg);
            return ResponseEntity.badRequest().body(msg);
        }
    }

    @PostMapping("signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        UserEntity user = userService.getByCredentials(userDTO.getUsername(), userDTO.getPassword(), passwordEncoder);
        if(user != null) {
            final String token = tokenProvider.create(user);
            final UserDTO responseUserDTO = UserDTO.builder().username(user.getUsername()).id(user.getId()).token(token).build();
            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            String msg = "Member sign-in failed.";
            log.error(msg);
            return ResponseEntity.badRequest().body(msg);
        }
    }
}
