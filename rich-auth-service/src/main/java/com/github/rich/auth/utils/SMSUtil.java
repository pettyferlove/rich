package com.github.rich.auth.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Petty
 */
public class SMSUtil {

    private static final int SMS_CODE_LENGTH = 6;

    private final static Pattern P = Pattern.compile("^(13[0-9]|15[012356789]|17[0-9]|18[0-9]|14[57])[0-9]{8}$");

    public static String createRandomVcode() {
        StringBuilder vcode = new StringBuilder();
        for (int i = 0; i < SMS_CODE_LENGTH; i++) {
            vcode.append(new Random().nextInt(10));
        }
        return vcode.toString();
    }

    /**
     * 校验手机号是否合法
     */
    public static Boolean isMobile(String number) {
        boolean flag = false;
        try {
            Matcher m = P.matcher(number);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
