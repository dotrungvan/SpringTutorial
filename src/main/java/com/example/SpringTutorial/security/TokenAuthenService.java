package com.example.SpringTutorial.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenService {

	private static long EXPIRATIONTIME = 864_000_000;

	private static String SECRET = "ThisIsASecret";

	public static String generateAccessToken(String username) {
		String JWT = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		return JWT;
	}

	public static String validateToken(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		Assert.hasText(token, "Token can't be empty");
		String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
		return user;
	}
}
