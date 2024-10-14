package com.neeraj.library_mmgt_system.config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenProvider {

    private final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        String jwt = Jwts.builder()
                .setSubject(userDetails.getUsername()) // Store the username as the subject
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + JwtConstant.EXPIRATION_TIME)) // Use the constant for expiration
                .claim("roles", populateAuthorities(auth.getAuthorities())) // Add roles as a claim
                .signWith(key)
                .compact();

        return jwt;
    }

    public String getUsernameFromJwtToken(String jwt) {
        // Remove "Bearer " prefix if it’s there
        if (jwt.startsWith(JwtConstant.TOKEN_PREFIX)) {
            jwt = jwt.substring(JwtConstant.TOKEN_PREFIX.length());
        }

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            // Log token validation error or handle it as needed
            return false;
        }
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            auths.add(authority.getAuthority());
        }
        return String.join(",", auths);
    }
    public String getEmailFromJwtToken(String jwt) {
        // Remove "Bearer " prefix if it’s there
        if (jwt.startsWith(JwtConstant.TOKEN_PREFIX)) {
            jwt = jwt.substring(JwtConstant.TOKEN_PREFIX.length());
        }

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        return claims.get("email", String.class); // Extract email from the claims
    }

}

