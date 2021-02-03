package com.revature.firebaseSecurity.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.revature.firebaseSecurity.UnauthenticatedFirebaseUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

public class FirebaseAuthenticationManager implements AuthenticationManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final FirebaseAuth firebaseAuth;

    public FirebaseAuthenticationManager(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof UnauthenticatedFirebaseUser) {
            String credentials = (String) authentication.getCredentials();
            if (StringUtils.hasText(credentials)) {
                try {
                    FirebaseToken token = firebaseAuth.verifyIdToken(credentials);
                    return new FirebaseUserDAO(token);
                } catch (FirebaseAuthException e) {
                    logger.error("Failed to authenticate user", e);
                    throw new BadCredentialsException("Invalid Credentials");
                }
            }
        }
        logger.warn("Found unknown token type: {}", authentication);
        throw new BadCredentialsException("Invalid Credentials");
    }
}