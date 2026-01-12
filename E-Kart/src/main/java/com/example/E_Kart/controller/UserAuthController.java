package com.example.E_Kart.controller;

import com.example.E_Kart.model.UserAuthEntity;
import com.example.E_Kart.model.dto.LoginRequestDto;
import com.example.E_Kart.security.JWTUtil;
import com.example.E_Kart.service.UserAuthEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthEntityService userAuthEntityService;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager; // ADD THIS
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserAuthEntity userAuthEntity){
        userAuthEntity.setPassword(passwordEncoder.encode(userAuthEntity.getPassword()));
        userAuthEntityService.save(userAuthEntity);
        return  ResponseEntity.ok("User registered sucessfully");
    }

    @GetMapping("/users")
    public String getUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "fetched user details sucessfully";
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginDto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );
        String accessToken = jwtUtil.generateToken(auth.getName(), 15);
        String refreshToken = jwtUtil.generateRefreshToken(auth.getName());
        userAuthEntityService.updateRefreshToken(auth.getName(), refreshToken);

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        ));
}

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        String username = jwtUtil.validateAndGetUsername(refreshToken);

        if (username != null) {
            // Verify the token exists in DB and is assigned to this user
            boolean isValid = userAuthEntityService.isRefreshTokenValid(username, refreshToken);

            if (isValid) {
                String newAccessToken = jwtUtil.generateToken(username, 15);
                return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
            }
        }
        return ResponseEntity.status(401).body("Invalid Refresh Token");
    }
}
