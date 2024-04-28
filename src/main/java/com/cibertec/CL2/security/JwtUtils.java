package com.cibertec.CL2.security;

import com.cibertec.CL2.models.EmployeeEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long tokenExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(EmployeeEntity employee) {
        return generateToken(new HashMap<>(), employee);
    }

    public String generateToken(Map<String, Object> extraClaims, EmployeeEntity employee) {
        return buildToken(extraClaims, employee, this.tokenExpiration);
    }

    public String generateRefreshToken(EmployeeEntity employee) {
        return buildToken(new HashMap<>(), employee, this.refreshTokenExpiration);
    }

    public String buildToken(Map<String, Object> extraClaims, EmployeeEntity employee, long expiration) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(employee.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .encryptWith(getSignInKey(), Jwts.ENC.A128CBC_HS256)
                .compact();
    }

    public boolean isTokenValid(String token, String username) {
        final String matchUsername = extractUsername(token);
        return (matchUsername.equals(username)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .decryptWith(getSignInKey())
                .build()
                .parseEncryptedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
