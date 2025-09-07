package com.shoppingcart.app.security.jwt;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;

@Component
@Slf4j
public class JWTUtils {
    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    //Method for creating tokens
    public String generateAccessToken(String username){
        return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
        .signWith(getSignaturKey(), SignatureAlgorithm.HS256)
        .compact();
    }

    //Get token sign
    public Key getSignaturKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //Validate token access
    public boolean isTokenValid(String token){
        try {
            Jwts.parserBuilder()
            .setSigningKey(getSignaturKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
            return true;
        } catch (Exception e) {
            log.error("Invalid token. Error: " .concat(e.getMessage()));
            return false;
        }
    }

    //Get all claims
    public Claims extractClaims(String token){
        return Jwts.parserBuilder()
        .setSigningKey(getSignaturKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    }

    //Get a single claim
    public <T> T getClaim(String token, Function<Claims, T> claimsFunction){
        Claims claims = extractClaims(token);
        return claimsFunction.apply(claims);
    }

    //Get username from token
    public String getUserNameFromToken(String token){
        return getClaim(token, Claims::getSubject);
    }
}
