package com.acme.messenger.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.Date;


@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;


    @Value("${jwt.issuer}")
    private String issuer;


    @Value("${jwt.access-ttl}")
    private String accessTtl; // ISO-8601 duration e.g. PT30M


    @PostConstruct
    public void init() {
// noop
    }

    public String generateToken(String subject, long ttlMs) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + ttlMs);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }


    public Claims parse(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
    }
}