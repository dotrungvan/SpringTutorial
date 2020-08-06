package com.example.SpringTutorial.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.SpringTutorial.security.TokenAuthenService;
import com.example.SpringTutorial.security.model.AccountDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	public JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
		String jsonBody = scanner.hasNext() ? scanner.next() : "";
		ObjectMapper objectMapper = new ObjectMapper();
		AccountDTO account = objectMapper.readValue(jsonBody, AccountDTO.class);
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(account.getUsername(),
				account.getPassword(), Collections.emptyList()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		response.sendRedirect("/home?access_token=" + TokenAuthenService.generateAccessToken(authResult.getName()));
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		System.out.println("JWTLoginFilter: Login fail");
		super.unsuccessfulAuthentication(request, response, failed);
	}
}
