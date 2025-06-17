package com.temani.temani.common.security;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.temani.temani.features.profile.domain.model.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {

    @Value("${temani.app.jwtSecret}")
    private String jwtSecret;

    @Value("${temani.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String getUsername(String token){
        JwtParser jwtParser = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes())).build();
        Claims claims = jwtParser.parse(token).accept(Jws.CLAIMS).getPayload();
        return claims.getSubject();
    }

    public String generateJwtToken(String username, String userId, Set<Role> roles) {
        Set<String> rolesNames = roles.stream().map(Role::getName).collect(Collectors.toSet());
        return Jwts.builder()
                .subject(username)
                .claim("id", userId)
                .claim("roles", rolesNames)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();
    }

    public boolean validateToken(String authToken){
        try{
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes())).build().parse(authToken);
            return true;
        }catch(SignatureException e){
            System.out.println("Invalid JWT signature: " + e.getMessage());
        }catch(IllegalArgumentException e){
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }catch(MalformedJwtException e){
            System.out.println("Invalid JWT token: " + e.getMessage());
        }catch(ExpiredJwtException e){
            System.out.println("JWT token is expired: " + e.getMessage());
        }catch(UnsupportedJwtException e){
            System.out.println("JWT token is unsupported: " + e.getMessage());
        }
        return false;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        return (bearer != null && bearer.startsWith("Bearer ")) ? bearer.substring(7) : null;
    }
}
