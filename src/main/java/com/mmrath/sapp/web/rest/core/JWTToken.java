package com.mmrath.sapp.web.rest.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mmrath.sapp.domain.core.User;

/**
 * Object to return as body in JWT Authentication
 */
public class JWTToken {
    private String idToken;
    private User user;

    public JWTToken(String idToken, User user) {
        this.idToken = idToken;
        this.user = user;
    }

    @JsonProperty("id_token")
    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
