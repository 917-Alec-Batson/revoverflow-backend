package com.revature.firebaseSecurity;

import org.springframework.security.core.GrantedAuthority;

public enum RevOverflowRole implements GrantedAuthority {
	ROLE_USER("ROLE_USER"),

	ROLE_ADMIN("ROLE_ADMIN");

	private final String authority;

	RevOverflowRole(String authority) {
	    this.authority = authority;
	}

	@Override
	public String getAuthority() {
	    return authority;
	}
}
