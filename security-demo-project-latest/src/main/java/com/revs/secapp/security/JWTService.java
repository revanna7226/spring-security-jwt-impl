package com.revs.secapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@Service
@AllArgsConstructor
public class JWTService {

    @Value("${app.security.jwt.privateKeyPath}")
    private String privateKeyPath;

    @Value("${app.security.jwt.PublicKeyPath}")
    private String publicKeyPath;

    @Value("${app.security.jwt.tokenExpiration}")
    private long accessTokenExpiration;

    @Value("${app.security.jwt.refreshExpiration}")
    private long refreshTokenExpiration;

    private static final String TOKEN_TYPE = "token_type";
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public JWTService() throws Exception {
        this.privateKey = KeyUtils.loadPrivateKey("keys/local-only/private_key.pem");
        this.publicKey = KeyUtils.loadPublicKey("keys/local-only/public_key.pem");
    }

    public String generateAccessToken(final String username) {
        final Map<String, Object> claims = Map.of(TOKEN_TYPE, "ACCESS_TOKEN");
        return buildToken(username, claims, this.accessTokenExpiration);
    }

    public String generateRefreshToken(final String username) {
        final Map<String, Object> claims = Map.of(TOKEN_TYPE, "REFRESH_TOKEN");
        return buildToken(username, claims, this.refreshTokenExpiration);
    }

    private String buildToken(String username, Map<String, Object> claims,
                              long expiration) {
        return Jwts.builder()
            .claims(claims)
            .subject(username)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(this.privateKey)
            .compact();
    }
    
    public boolean isTokenValid(final String token, final String expectedUsername) {
        final String username = extractUsername(token);
        return username.equals(expectedUsername) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration()
            .before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                .verifyWith(this.publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (final JwtException e) {
            throw new RuntimeException("Invalid JWT Token");
        }
    }

    public String refreshAccessToken(final String refreshToken) {
        final Claims claims = extractClaims(refreshToken);

        if(claims.isEmpty() || !claims.get(TOKEN_TYPE).equals("REFRESH_TOKEN")) {
            throw new RuntimeException("Invalid Token Type");
        }

        if(isTokenExpired(refreshToken)) {
            throw new RuntimeException("Refresh Token Expired");
        }

        final String username = claims.getSubject();
        return generateAccessToken(username);
    }

}
