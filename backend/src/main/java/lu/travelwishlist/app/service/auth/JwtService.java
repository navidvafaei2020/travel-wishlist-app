package lu.travelwishlist.app.service.auth;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lu.travelwishlist.app.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for generating and validating JWT tokens.
 *
 * <p>
 * Provides methods to create tokens, extract user info, and verify token validity.
 */
@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;  // in milliseconds


    /**
     * Generates a JWT token for the given user.
     *
     * @param user the user entity
     * @return JWT token as a String
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());
        claims.put("role", user.getRole().toString());

        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    /** Extracts the username from a JWT token. */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /** Extracts the user ID from a JWT token. */
    public Long extractUserId(String token) {
        return extractAllClaims(token).get("userId", Long.class);
    }

    /** Extracts the role from a JWT token. */
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    /** Validates a JWT token (checks signature and expiration). */
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    /** Checks if the token has expired. */
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }


    /** Extracts all claims from the token. */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /** Converts the secret key string into a SecretKey for signing JWTs. */
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}