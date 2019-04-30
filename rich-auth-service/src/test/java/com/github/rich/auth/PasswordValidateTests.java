package com.github.rich.auth;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;

public class PasswordValidateTests {

    /**
     * 验证密码
     */
    @Test
    public void passwordValidate() {
        System.out.println(new BCryptPasswordEncoder().encode("rich@123456"));
        System.out.println(new BCryptPasswordEncoder().matches("admin", "$2a$10$ruJZU6MaoqoTGstpiBs5P.y3OIs0f3H01iyf31qZz1NnS/BxrHMOa"));
    }

}
