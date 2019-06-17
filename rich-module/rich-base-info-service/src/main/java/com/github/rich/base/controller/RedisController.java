package com.github.rich.base.controller;

import com.github.rich.common.core.vo.R;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.swagger.annotations.Api;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

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
            optional.get().getConnection().hyperLogLogCommands();
        }
        List<Object> slowlogs = (List<Object>) redisTemplate.execute(new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                System.out.println(connection.getClass());
                RedisAsyncCommands redisAsyncCommands = (RedisAsyncCommands) connection.getNativeConnection();
                RedisFuture<List<Object>> future = redisAsyncCommands.slowlogGet(10);
                try {
                    return future.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        System.out.println(slowlogs);
        return new R<>(info);
    }
}
