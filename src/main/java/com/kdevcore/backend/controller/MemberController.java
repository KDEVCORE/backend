package com.kdevcore.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdevcore.backend.dto.MemberDTO;
import com.kdevcore.backend.model.MemberEntity;
import com.kdevcore.backend.security.TokenProvider;
import com.kdevcore.backend.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Bean으로 작성해도 됨

    @PostMapping("signup")
    public ResponseEntity<?> registerUser(@RequestBody MemberDTO memberDTO) {
        try {
            if(memberDTO == null || memberDTO.getPassword() == null) throw new RuntimeException("Invalid Password value");
            MemberEntity user = MemberEntity.builder().username(memberDTO.getUsername()).password(passwordEncoder.encode(memberDTO.getPassword())).build();
            MemberEntity registeredUser = memberService.create(user);
            MemberDTO responseUserDTO = MemberDTO.builder().id(registeredUser.getId()).username(registeredUser.getUsername()).build();
            return ResponseEntity.ok().body(responseUserDTO);
        } catch(Exception e) {
            String msg = "Member registration failed: " + e.getMessage();
            log.error(msg);
            return ResponseEntity.badRequest().body(msg);
        }
    }

    @PostMapping("signin")
    public ResponseEntity<?> authenticate(@RequestBody MemberDTO memberDTO) {
        MemberEntity user = memberService.getByCredentials(memberDTO.getUsername(), memberDTO.getPassword(), passwordEncoder);
        if(user != null) {
            final String token = tokenProvider.create(user);
            final MemberDTO responseUserDTO = MemberDTO.builder().username(user.getUsername()).id(user.getId()).token(token).build();
            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            String msg = "Member sign-in failed.";
            log.error(msg);
            return ResponseEntity.badRequest().body(msg);
        }
    }
}
