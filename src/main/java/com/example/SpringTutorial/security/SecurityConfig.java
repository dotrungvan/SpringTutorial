package com.example.SpringTutorial.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
		http.csrf().disable().authorizeRequests().antMatchers("/api/v1/students/**")
				.hasRole(ApplicationUserRole.ADMIN.name()).antMatchers(HttpMethod.POST, "/api/v1/students/**")
				.hasRole(ApplicationUserRole.ADMIN.name()).anyRequest().authenticated().and().formLogin()
				.usernameParameter("username").passwordParameter("password")
//				.loginPage("/login").permitAll()
				.defaultSuccessUrl("/home",true).and().rememberMe().rememberMeParameter("remember-me");
				;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password(this.passwordEncoder().encode("password"))
				.authorities(ApplicationUserRole.ADMIN.getGrantedAuthority()).and()
				.withUser("student").password(this.passwordEncoder().encode("password"))
				.authorities(ApplicationUserRole.STUDENT.getGrantedAuthority()).and()
				.withUser("teacher").password(this.passwordEncoder().encode("password"))
				.authorities(ApplicationUserRole.TEACHER.getGrantedAuthority());
	}
}