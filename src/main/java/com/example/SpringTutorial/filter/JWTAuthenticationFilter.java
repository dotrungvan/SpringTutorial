package com.example.SpringTutorial.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import com.example.SpringTutorial.security.TokenAuthenService;

public class JWTAuthenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("Authentication Filter: filter");
		Assert.hasText(TokenAuthenService.validateToken((HttpServletRequest) request),"Token invalid");
		chain.doFilter(request, response);
	}

}
