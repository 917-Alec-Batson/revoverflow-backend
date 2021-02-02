package com.revature.firebaseSecurity;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Map;

public class FirebaseUser extends AbstractAuthenticationToken {

    private final Map<String, Object> claims;

    public FirebaseUser(Map<String, Object> claims) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.claims = claims;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return claims.getOrDefault("email", "");
    }

    @Override
    public String getName() {
        return (String) claims.getOrDefault("email", "");
    }
}