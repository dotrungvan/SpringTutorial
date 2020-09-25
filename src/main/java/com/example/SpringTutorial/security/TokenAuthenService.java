package com.example.SpringTutorial.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenService {

	private static long EXPIRATIONTIME = 864_000_000;

	private static String SECRET = "ThisIsASecret";

	private static final String TOKEN_PREFIX = "Bearer";

	public static String generateAccessToken(Authentication user) {
		String JWT = Jwts.builder().setAudience(user.getName()).setSubject(user.getAuthorities().toString())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		return JWT;
	}

	public static Authentication validateToken(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		List<SimpleGrantedAuthority> lstRole = new ArrayList<SimpleGrantedAuthority>();
		Assert.hasText(token, "Token can't be empty");
		Claims infomationToken = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
				.getBody();
		String user = infomationToken.getAudience();
		String roleStr = infomationToken.getSubject();
		if (StringUtils.hasLength(roleStr) && roleStr.contains(",")) {
			String[] arrRole = roleStr.substring(1, roleStr.length() - 1).split(",");
			for (int i = 0; i < arrRole.length; i++) {
				lstRole.add(new SimpleGrantedAuthority(arrRole[i]));
			}
		}
		return user != null ? new UsernamePasswordAuthenticationToken(user, null, lstRole) : null;
	}
}
