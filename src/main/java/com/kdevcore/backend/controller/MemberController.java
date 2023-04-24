package com.kdevcore.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdevcore.backend.dto.UserDTO;
import com.kdevcore.backend.model.UserEntity;
import com.kdevcore.backend.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Member")
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Operation(summary = "Member profile getting process", description = "회원 정보 불러오기")
    @GetMapping("/profile")
    public ResponseEntity<?> retrieveProfile(@AuthenticationPrincipal String userIdentifier) {
        UserEntity userEntity = memberService.retrieve(userIdentifier);
        UserDTO dto = new UserDTO(userEntity);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Member profile update process", description = "회원 정보 수정하기")
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserEntity userEntity = memberService.update(UserDTO.toEntity(userDTO));
        UserDTO dto = new UserDTO(userEntity);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Member profile delete process", description = "회원 정보 삭제하기")
    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteProfile(@RequestBody UserDTO userDTO) {
        Boolean check = memberService.delete(UserDTO.toEntity(userDTO));
        return ResponseEntity.ok().body(check);
    }
}
