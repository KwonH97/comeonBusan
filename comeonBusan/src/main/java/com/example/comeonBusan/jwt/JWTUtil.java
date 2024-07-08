package com.example.comeonBusan.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JWTUtil {

	private SecretKey secretKey;

	public JWTUtil(@Value("${spring.jwt.secret}") String secret) {

		this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
		
	}
	
	//검증
	
	public String getUsername(String token) {
		
		
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
		
	}
	
	public String getRole(String token) {
		
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
	}
	
	public Boolean isExpired(String token) {
		
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
	}
	
	
	
	// 토큰 생성
    public String createJwt(String username, String role, Long expiredMs) {
    	
    	System.out.println("토큰 생성하는 곳 " + username);
    	System.out.println("토큰 생성하는 곳 " + role);
    	
    	//expiredMs = 24 * 60 * 60 * 1000L; // 24시간

        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) // 토큰이 발행된 시간(현재시간)
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) // 토큰이 만료되는 시간
                .signWith(secretKey)
                .compact();
    }
    

}

