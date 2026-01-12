package com.example.E_Kart.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {
    private static final String secret_key ="yfiuygjhwueihliefw5fadgre48ewf5a1458ar4e49FW9Fe26e";
    private static final Key key = Keys.hmacShaKeyFor(secret_key.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String username, long expiryMinutes){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiryMinutes *60 *1000))
                .signWith(key)
                .compact();
    }
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String validateAndGetUsername(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith((javax.crypto.SecretKey) key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

              if (claims.getExpiration().before(new Date())) {
                return null;
            }

            return claims.getSubject(); // This returns the username
        } catch (Exception e) {
            return null;
        }
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 7 Days
                .signWith(key)
                .compact();
    }
}
