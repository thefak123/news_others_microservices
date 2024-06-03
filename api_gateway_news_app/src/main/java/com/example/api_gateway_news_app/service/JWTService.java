package com.example.api_gateway_news_app.service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.api_gateway_news_app.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
    private final String SECRET_KEY = "cbd3cfb9b9f51bbbfbf08759e243f5b3519cbf6ecc219ee95fe7c667e32c0a8d";

    public String extractEmail(String token){
        // How Claims::getSubject get username ? Look at the generateToken(User user) method in this class
        // It sets subject as username
        return extractClaims(token, Claims::getSubject);

    }

    public boolean isValid(String token, UserDetails user){
        String email = extractEmail(token);
        // extractExpirationDate(token).before(new Date()) -> check if current date is before expiration date
        return (email.equals(user.getUsername()) && !extractExpirationDate(token).before(new Date()));

    }

    public Date extractExpirationDate(String token){
        // How Claims::getExpiration get expiration date ? Look at the generateToken(User user) method in this class
        // It sets expiration date 
        return extractClaims(token, Claims::getExpiration);

    }
    public <T> T extractClaims(String token, Function<Claims, T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();

    }

    public String generateToken(User user){
        String token = Jwts.builder().subject(user.getEmail()).issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 24*1000*60*60)).signWith(getSigningKey()).compact();
        return token;
    }

    private SecretKey getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}