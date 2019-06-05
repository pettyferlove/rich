package com.github.rich.common.core.utils;

import com.github.rich.common.core.constants.CommonConstant;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 常用方法工具类
 * @author Petty
 */
public class CommonUtils {

    /**
     * 格式化日期
     *
     * @param dateStr      源数据
     * @param sourceFormat 源格式
     * @param targetFormat 目标格式
     * @return 转换后的字符串
     */
    public static String formatDate(String dateStr, String sourceFormat, String targetFormat) {
        String result = "";
        if (StringUtils.isNoneEmpty(dateStr) && StringUtils.isNoneEmpty(sourceFormat) && StringUtils.isNoneEmpty(targetFormat)) {
            SimpleDateFormat sFormat = new SimpleDateFormat(sourceFormat);
            SimpleDateFormat tFormat = new SimpleDateFormat(targetFormat);
            ParsePosition pos = new ParsePosition(0);
            Date dateTemp = sFormat.parse(dateStr, pos);
            result = tFormat.format(dateTemp);
        }
        return result;
    }

    /**
     * 转化时间格式(Date类型)
     *
     * @param date Date
     * @return 转换后的字符串
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(date);
        }
    }

    /**
     * 获取32位uuid
     *
     * @return UUID
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取Ip地址
     *
     * @param request Request
     * @return IP地址
     */
    public static String getIpAdrress(HttpServletRequest request) {
        String xIp = request.getHeader("X-Real-IP");
        String xFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(xFor) && !CommonConstant.UN_KNOWN_CLIENT_IP.equalsIgnoreCase(xFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = xFor.indexOf(",");
            if (index != -1) {
                return xFor.substring(0, index);
            } else {
                return xFor;
            }
        }
        xFor = xIp;
        if (StringUtils.isNotEmpty(xFor) && !CommonConstant.UN_KNOWN_CLIENT_IP.equalsIgnoreCase(xFor)) {
            return xFor;
        }
        if (StringUtils.isBlank(xFor) || CommonConstant.UN_KNOWN_CLIENT_IP.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(xFor) || CommonConstant.UN_KNOWN_CLIENT_IP.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(xFor) || CommonConstant.UN_KNOWN_CLIENT_IP.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(xFor) || CommonConstant.UN_KNOWN_CLIENT_IP.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(xFor) || CommonConstant.UN_KNOWN_CLIENT_IP.equalsIgnoreCase(xFor)) {
            xFor = request.getRemoteAddr();
        }
        return xFor;
    }


}
