package com.github.rich.auth;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UUIDTests {

    @Test
    public void createUUID(){
        System.out.println(UUID.randomUUID().toString().replace("-",""));
    }
}
