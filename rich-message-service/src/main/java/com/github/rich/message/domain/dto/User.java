package com.github.rich.message.domain.dto;

import java.security.Principal;

/**
 * 重写Principal，用User UUID作为WebSocket认证过程中的标识符
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
