package com.github.rich.auth;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordValidateTests {

    /**
     * 验证密码
     */
    @Test
    public void passwordValidate() {
        System.out.println(new BCryptPasswordEncoder().encode("zcdx@123"));
        System.out.println(new BCryptPasswordEncoder().matches("admin", "$2a$10$ruJZU6MaoqoTGstpiBs5P.y3OIs0f3H01iyf31qZz1NnS/BxrHMOa"));
    }

}
