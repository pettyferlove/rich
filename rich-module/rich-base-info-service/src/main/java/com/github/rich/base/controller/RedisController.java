package com.github.rich.base.controller;

import com.github.rich.common.core.vo.R;
import io.swagger.annotations.Api;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Properties;

/**
 * @author Petty
 */
@Api(value = "Redis状态信息", tags = {"Redis状态接口"})
@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisTemplate redisTemplate;

    public RedisController(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping
    public R<Properties> get() {
        Properties info = new Properties();
        Optional<RedisConnectionFactory> optional = Optional.ofNullable(redisTemplate.getConnectionFactory());
        if (optional.isPresent()) {
            info = optional.get().getConnection().info();
        }
        return new R<>(info);
    }
}
