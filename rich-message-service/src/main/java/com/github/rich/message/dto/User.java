package com.github.rich.message.dto;

import java.security.Principal;

/**
 * @author Petty
 */
public class User implements Principal {

    private String userId;

    public User(String userId) {
        this.userId = userId;
    }

    @Override
    public String getName() {
        return this.userId;
    }
}
