package fr.esgi.avis.infrastructure.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final Duration  expiration;

    public JwtTokenProvider(
            @Value("${security.jwt.secret:change-me-super-secret-key-esgi-2026-minimum-32-chars}") String secret,
            @Value("${security.jwt.expiration-hours:24}") long expirationHours) {
        this.secretKey  = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = Duration.ofHours(expirationHours);
    }

    public String generateToken(String email, String role) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(expiration)))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try { parseClaims(token); return true; }
        catch (JwtException | IllegalArgumentException e) { return false; }
    }

    public String getEmailFromToken(String token) { return parseClaims(token).getSubject(); }
    public String getRoleFromToken(String token)  { return parseClaims(token).get("role", String.class); }

    private Claims parseClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload();
    }
}
