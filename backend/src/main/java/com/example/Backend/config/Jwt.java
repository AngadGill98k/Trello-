package com.example.Backend.config;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.Backend.models.User;
import com.example.Backend.repository.User_repo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class Jwt {
//    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);   this will not work crate random strign when teh sprig app reloads so token brak
    private Key key;
    private String secret="Your32+CharacterSecretKeyHereThatIsSecuresecerekey";
    private final long expirationMs = 1000 * 60 * 60; // 1 hour
    private User_repo user_repo;
    public Jwt(User_repo user_repo) {
        this.user_repo = user_repo;
        this.key = Keys.hmacShaKeyFor(this.secret.getBytes());
    }
    // Generate JWT token
    public String access_Token(String id) {
        User user= user_repo.findById(id).get();
        String name=user.getName();
        String mail=user.getMail();
        return Jwts.builder()
                .setSubject(id)
                .claim("name",name)
                .claim("mail",mail)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key)
                .compact();
    }

    public String refresh_Token(String id) {
        return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (expirationMs * 7)))
                .signWith(key)
                .compact();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // Validate and parse token
    public String extract_token(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

        } catch (Exception e) {
            Log.log.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
