package com.github.rich.auth.utils;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordValidateUtilsTests {

    private static PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private static PasswordEncoder sha1 = new MessageDigestPasswordEncoder("SHA-1");

    private static PasswordEncoder sha256 = new MessageDigestPasswordEncoder("SHA-256");

    private static PasswordEncoder md5 = new MessageDigestPasswordEncoder("MD5");

    /**
     * 验证密码
     */
    @Test
    public void md5() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            md5.matches("edaSecret", "{i9Xt+fQL48DYxyYBKmbD28PBqFhTmlyWTyCkL6CPle8=}b3f08044d4789294d9b906cad7d3f8ea");
        }
        System.out.println("md5===>" + (System.currentTimeMillis() - start));
    }

    @Test
    public void bcrypt() {
        long start = System.currentTimeMillis();
        bCryptPasswordEncoder.matches("123456", "$2a$10$nM4jZNN6Qp3cTKf91gKhte/QxXVPxg9p5Bhh5Y1D8CkOgWlIoZkkG");
        System.out.println("bcrypt===>" + (System.currentTimeMillis() - start));
    }

    @Test
    public void sha1() {
        long start = System.currentTimeMillis();
        sha1.matches("123456", "{hKam1q97tviv5gZmytKAxH1wjm4SwuRqFMp9/nyalHA=}26288142ba0692d56b933897f8128d07e7e48d4f9fcfa01dbf5bbaabfa93b896");
        System.out.println("sha1===>" + (System.currentTimeMillis() - start));
    }

    @Test
    public void sha256() {
        long start = System.currentTimeMillis();
        sha256.matches("123456", "{SHA-256}{58quyF11wAV8xm+PJSoZHdHjlTSYIJonTdHXxNpNdWI=}2c0cfc1e7d5adfcf2b9c140094c9d1d92ab28df093f0a79433416a6eeb3bcf2c");
        System.out.println("sha256===>" + (System.currentTimeMillis() - start));
    }

    @Test
    public void createMd5Password() {
        System.out.println("create md5 password start");
        System.out.println(md5.encode("123456"));
        System.out.println(md5.encode("rich@123456"));
        System.out.println("create md5 password end");
    }

    //@Test
    public void createBCryptPassword() {
        System.out.println("create bcrypt password start");
        System.out.println(bCryptPasswordEncoder.encode("123456"));
        System.out.println(bCryptPasswordEncoder.encode("123456"));
        System.out.println("create bcrypt password end");
    }

    //@Test
    public void createSha1Password() {
        System.out.println("create bcrypt password start");
        System.out.println(sha1.encode("123456"));
        System.out.println(sha1.encode("123456"));
        System.out.println("create bcrypt password end");
    }

    @Test
    public void createSha256Password() {
        System.out.println("create bcrypt password start");
        System.out.println(sha256.encode("123456"));
        System.out.println(sha256.encode("rich@123456"));
        System.out.println("create bcrypt password end");
    }

}
