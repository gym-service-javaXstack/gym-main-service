package com.example.springcore.util;


import com.example.springcore.model.User;
import com.example.springcore.redis.model.RedisTokenModel;
import com.example.springcore.redis.service.RedisTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtTokenService {

    @Value("${token.signing.key}")
    private String jwtSigningKey;

    private final RedisTokenService redisTokenService;

    public JwtTokenService(RedisTokenService redisTokenService) {
        this.redisTokenService = redisTokenService;
    }

    public String generateToken(UserDetails userDetails) {
        log.info("Enter JwtTokenService generateToken method: {}", userDetails);

        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("firstName", customUserDetails.getFirstName());
            claims.put("lastName", customUserDetails.getLastName());
        }
        String token = generateToken(claims, userDetails);
        RedisTokenModel redisTokenModel = new RedisTokenModel(token);

        redisTokenService.saveUserToken(userDetails.getUsername(), redisTokenModel);

        log.info("Exit JwtTokenService redisTokenModel: {}", redisTokenModel);
        return token;
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        log.info("Enter JwtTokenService validateToken method");
        final String username = extractUsername(token);
        RedisTokenModel redisTokenModel = redisTokenService.findUserTokenByUsername(username);
        if (redisTokenModel == null) {
            log.info("Exit JwtTokenService validateToken method");
            return false;
        }
        log.info("Exit JwtTokenService validateToken method");
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && token.equals(redisTokenModel.getToken()));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void delete(String username) {
        redisTokenService.deleteUserToken(username);
    }
}
