package com.pocalink.ddingcon.global.security;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class TmpJwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.accesstoken-validity-in-seconds}")
    private long accessTokenExpirationTime;
    @Value("${jwt.refreshtoken-validity-in-seconds}")
    private long refreshTokenExpirationTime;

    public String createAccessToken(Long memberId) {
        return createToken(memberId.toString(), accessTokenExpirationTime);
    }

    public String createRefreshToken(Long memberId) {
        return createToken(memberId.toString(), refreshTokenExpirationTime);
    }

    public String createToken(String payload, long expirationTime) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationTime))
                .signWith(hmacShaKeyFor(secretKey.getBytes(UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring("Bearer ".length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes(UTF_8))
                    .build()
                    .parseClaimsJws(token);
            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (ExpiredJwtException e) {
            return false;
        } catch (JwtException e) {
            return false;
        }
    }
}
