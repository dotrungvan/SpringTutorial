package com.example.SpringTutorial.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
			.antMatchers("/home").hasRole("ADMIN")
			.antMatchers("/api/v1/students").hasRole("ADMIN")
			.anyRequest().authenticated().and().httpBasic();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password(this.passwordEncoder().encode("password"))
				.roles(ApplicationUserRole.ADMIN.name());
		auth.inMemoryAuthentication().withUser("student").password(this.passwordEncoder().encode("password"))
				.roles(ApplicationUserRole.STUDENT.name());
		auth.inMemoryAuthentication().withUser("teacher").password(this.passwordEncoder().encode("password"))
				.roles(ApplicationUserRole.TEACHER.name());
	}
}