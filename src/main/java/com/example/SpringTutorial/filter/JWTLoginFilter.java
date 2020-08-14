package com.example.SpringTutorial.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.SpringTutorial.security.TokenAuthenService;
import com.example.SpringTutorial.security.model.AccountDTO;
import com.example.SpringTutorial.util.APIConst;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	ObjectMapper objectMapper = new ObjectMapper();

	public JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(request.getInputStream(), StandardCharsets.UTF_8.name()).useDelimiter("\\A");
		String jsonBody = scanner.hasNext() ? scanner.next() : "";
		AccountDTO account = null;
		try {
			account = objectMapper.readValue(jsonBody, AccountDTO.class);
		} catch (Exception ex) {
			throw new InternalAuthenticationServiceException("Value must be Json format");
		}
		response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(account.getUsername(),
				account.getPassword(), Collections.emptyList()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		Map<String, String> token = new HashMap<String, String>();
		token.put(APIConst.ACCESS_TOKEN, TokenAuthenService.generateAccessToken(authResult));
		response.getWriter().write(objectMapper.writeValueAsString(token));
		response.getWriter().flush();
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		response.sendError(HttpStatus.UNAUTHORIZED.value(), failed.getMessage());
	}

}
