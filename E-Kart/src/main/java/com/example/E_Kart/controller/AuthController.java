package com.example.E_Kart.controller;

import com.example.E_Kart.model.dto.LoginRequestDto;
import com.example.E_Kart.model.dto.LoginResponseDto;
import com.example.E_Kart.model.dto.SignUpRequestDto;
import com.example.E_Kart.model.dto.SignUpResponseData;
import com.example.E_Kart.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseData> signup(@RequestBody SignUpRequestDto signupRequestDto) {

        return ResponseEntity.ok(authService.signup(signupRequestDto));
    }

}
