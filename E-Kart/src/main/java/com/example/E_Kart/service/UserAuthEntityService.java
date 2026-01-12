package com.example.E_Kart.service;

import com.example.E_Kart.model.UserAuthEntity;
import com.example.E_Kart.repo.UserAuthEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserAuthEntityService implements UserDetailsService {
    private final UserAuthEntityRepository userAuthEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthEntity user = userAuthEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword()) // This MUST be the hashed password from DB
                .authorities(user.getRole())
                .build();
    }


    public UserDetails save(UserAuthEntity userAuthEntity) {

        return userAuthEntityRepository.save(userAuthEntity);
    }
    public void updateRefreshToken(String username, String refreshToken) {
        UserAuthEntity user = userAuthEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setRefreshToken(refreshToken);
        // Setting expiry to 7 days from now
        user.setRefreshTokenExpiry(LocalDateTime.now().plusDays(7));
        userAuthEntityRepository.save(user);
    }

    public boolean isRefreshTokenValid(String username, String refreshToken) {
        return userAuthEntityRepository.findByUsername(username)
                .map(user -> user.getRefreshToken() != null &&
                        user.getRefreshToken().equals(refreshToken) &&
                        user.getRefreshTokenExpiry().isAfter(LocalDateTime.now()))
                .orElse(false);
    }

}
