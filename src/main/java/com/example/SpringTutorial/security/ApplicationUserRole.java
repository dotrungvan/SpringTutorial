package com.example.SpringTutorial.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum ApplicationUserRole {
	ADMIN(new HashSet<ApplicationUserPermission>(
			Arrays.asList(ApplicationUserPermission.COURSE_READ, ApplicationUserPermission.COURSE_WRITE,
					ApplicationUserPermission.STUDENT_READ, ApplicationUserPermission.STUDENT_WRITE))),
	TEACHER(new HashSet<ApplicationUserPermission>(
			Arrays.asList(ApplicationUserPermission.STUDENT_READ, ApplicationUserPermission.STUDENT_WRITE))),
	STUDENT(new HashSet<ApplicationUserPermission>());

	private Set<ApplicationUserPermission> permission;

	private ApplicationUserRole(Set<ApplicationUserPermission> permission) {
		this.permission = permission;
	}

	public Set<ApplicationUserPermission> getPermission() {
		return permission;
	}

	public List<GrantedAuthority> getGrantedAuthority() {
		List<GrantedAuthority> auth = getPermission().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
				.collect(Collectors.toList());
		auth.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return auth;
	}

}
