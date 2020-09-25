package com.example.SpringTutorial.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

}
