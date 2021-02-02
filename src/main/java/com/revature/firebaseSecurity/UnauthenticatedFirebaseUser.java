package com.revature.firebaseSecurity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UnauthenticatedFirebaseUser extends UsernamePasswordAuthenticationToken {

    public UnauthenticatedFirebaseUser(String bearerToken) {
        super(null, bearerToken);
    }
}