package com.github.rich.auth.service;

import com.github.rich.auth.service.CaptchaValidateService;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

/**
 * 抽象一个验证服务，默认实现校验功能
 *
 * @author Petty
 */
public abstract class AbstractCaptchaValidateService implements CaptchaValidateService {

    private final RedisTemplate redisTemplate;

    protected AbstractCaptchaValidateService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Boolean validate(String key, String code, String captcha) {
        boolean result = false;
        StringBuilder stringBuffer = new StringBuilder(key);
        stringBuffer.append(":");
        stringBuffer.append(code);
        try {
            Optional<String> redisValidateCodeOptional = Optional.ofNullable((String) redisTemplate.opsForValue().get(stringBuffer.toString()));
            String redisValidateCode = redisValidateCodeOptional.orElse("");
            if (captcha.equals(redisValidateCode)) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //判断完成后删除Key
            redisTemplate.delete(stringBuffer.toString());
            //清空
            stringBuffer.setLength(0);
        }
        return result;
    }
}
