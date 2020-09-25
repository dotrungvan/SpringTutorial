package com.example.SpringTutorial.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import com.example.SpringTutorial.security.TokenAuthenService;

import io.jsonwebtoken.SignatureException;

public class JWTAuthenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		try {
			System.out.println("Authentication Filter: filter");
			Authentication auth = TokenAuthenService.validateToken((HttpServletRequest) request);
			Assert.notNull(auth, "Token invalid");
			SecurityContextHolder.getContext().setAuthentication(auth);
			chain.doFilter(request, response);
		} catch (IllegalArgumentException ex) {
			httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
		} catch (SignatureException signatureex) {
			httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), signatureex.getMessage());
		}
	}

}
