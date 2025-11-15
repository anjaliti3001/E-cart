package com.example.E_Kart.security;

import com.example.E_Kart.model.User;
import com.example.E_Kart.model.dto.LoginRequestDto;
import com.example.E_Kart.model.dto.LoginResponseDto;
import com.example.E_Kart.model.dto.SignUpRequestDto;
import com.example.E_Kart.model.dto.SignUpResponseData;
import com.example.E_Kart.repo.UserRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final AuthenticationManager authenticationManager;

    private final AuthUtil authUtil;
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(token, user.getId());
    }



    public SignUpResponseData signup(SignUpRequestDto signUpRequestDto) {
        User existingUser = userRepository.findByUsername(signUpRequestDto.getUsername()).orElse(null);
        if (existingUser != null) {
            throw new IllegalArgumentException("User already exists");
        }

        User user = User.builder()
                .username(signUpRequestDto.getUsername())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .build();

        user = userRepository.save(user);

        return new SignUpResponseData(user.getId(), user.getUsername());
    }


}
