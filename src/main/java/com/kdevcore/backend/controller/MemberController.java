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
import com.kdevcore.backend.enums.Provider;
import com.kdevcore.backend.enums.Role;
import com.kdevcore.backend.model.MemberEntity;
import com.kdevcore.backend.model.UserEntity;
import com.kdevcore.backend.security.JwtProvider;
import com.kdevcore.backend.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Bean으로 작성해도 됨

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            if(userDTO == null || userDTO.getPassword() == null) throw new RuntimeException("Invalid Password value");
            UserEntity registeredUser = memberService.create(UserEntity.builder()
                                                                         .identifier(userDTO.getIdentifier())
                                                                         .password(passwordEncoder.encode(userDTO.getPassword()))
                                                                         .email(userDTO.getEmail())
                                                                         .name(userDTO.getName())
                                                                         .role(Role.ROLE_MEMBER)
                                                                         .build());
            UserDTO responseUserDTO = UserDTO.builder()
                                             .uuid(registeredUser.getUuid())
                                             .name(registeredUser.getName())
                                             .email(registeredUser.getEmail())
                                             .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } catch(Exception e) {
            String msg = "Member registration failed: " + e.getMessage();
            log.error(msg);
            return ResponseEntity.badRequest().body(msg);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        UserEntity user = memberService.getByCredentials(userDTO.getIdentifier(), userDTO.getPassword(), passwordEncoder);
        if(user != null) {
            final String token = jwtProvider.create(user);
            final UserDTO responseUserDTO = UserDTO.builder()
                                                   .uuid(user.getUuid())
                                                   .identifier(user.getIdentifier())
                                                   .token(token).build();
            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            String msg = "Member sign-in failed.";
            log.error(msg);
            return ResponseEntity.badRequest().body(msg);
        }
    }
}
