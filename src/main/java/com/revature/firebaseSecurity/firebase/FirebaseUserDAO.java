package com.revature.firebaseSecurity.firebase;

import com.google.firebase.auth.FirebaseToken;
import com.revature.firebaseSecurity.FirebaseUser;

public class FirebaseUserDAO extends FirebaseUser {
    public FirebaseUserDAO(FirebaseToken token) {
        super(token.getClaims());
    }
}
