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
import com.kdevcore.backend.enums.Role;
import com.kdevcore.backend.model.UserEntity;
import com.kdevcore.backend.security.JwtProvider;
import com.kdevcore.backend.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Authentication", description = "A controller that handles authentication")
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Bean으로 작성해도 됨

    @Operation(summary = "Member registration process handling", description = "회원 가입 프로세스 처리")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        log.info("Sign Up process: {}", userDTO.toString());
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
                                             .identifier(registeredUser.getIdentifier())
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

    @Operation(summary = "Member login process handling", description = "회원 로그인 프로세스 처리")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        log.info("Sign In process: {}", userDTO.toString());
        UserEntity user = memberService.getByCredentials(userDTO.getIdentifier(), userDTO.getPassword(), passwordEncoder);
        if(user != null) {
            log.info("Create token, user info: {}", user.toString());
            final String token = jwtProvider.create(user);
            final UserDTO responseUserDTO = UserDTO.builder()
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
