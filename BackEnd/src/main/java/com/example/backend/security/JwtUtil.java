package com.example.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final String SECRET = "MiClaveSecretaSuperSeguraYExtremadamenteLargaParaFirmarJWT123!";
    private final long EXPIRATION_TIME = 3600000;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generarToken(String username, String rol) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("rol", "ROLE_" + rol.toUpperCase());

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validarToken(String token, String username) {
        final String tokenUsername = extraerUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpirado(token));
    }

    public String extraerUsername(String token) {
        return extraerTodasLasReclamaciones(token).getSubject();
    }

    public String extraerRol(String token) {
        return (String) extraerTodasLasReclamaciones(token).get("rol");
    }

    private boolean isTokenExpirado(String token) {
        return extraerTodasLasReclamaciones(token).getExpiration().before(new Date());
    }

    private Claims extraerTodasLasReclamaciones(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}