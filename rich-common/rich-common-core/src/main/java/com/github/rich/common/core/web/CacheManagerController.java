package com.github.rich.common.core.web;

import com.github.rich.common.core.domain.vo.R;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 缓存管理接口
 * 由各个服务实现具体逻辑
 * @author Petty
 */
@RestController
@RequestMapping("/cache/manager")
public class CacheManagerController {

    private final RedisTemplate redisTemplate;

    public CacheManagerController(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 删除当前Redis DB全部缓存
     * 必须拥有ROLE_ADMIN角色才可以删除
     * @deprecated 该方法会直接删除当前服务下所有redis缓存，应尽量使用key进行匹配删除
     * @return 删除个数
     */
    @Deprecated
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/clear/all")
    public R<Long> clearAll(){
        Set<String> keys = new HashSet<>();
        this.scan("*", item -> {
            //符合条件的key
            keys.add(new String(item, StandardCharsets.UTF_8));
        });
        //Keys直接返回所有的key，大量Key存在的时候将会导致性能问题
        //<String> keys = redisTemplate.keys("*");
        return new R<>(redisTemplate.delete(keys));
    }

    /**
     * 根据Key匹配并删除Redis DB缓存
     * 必须拥有ROLE_ADMIN角色才可以删除
     * @return 删除个数
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/clear/{key}")
    public R<Long> clear(@PathVariable String key){
        Set<String> keys = new HashSet<>();
        this.scan(key.contains("*") ?key:key+"*", item -> {
            //符合条件的key
            keys.add(new String(item, StandardCharsets.UTF_8));
        });
        //Set<String> keys = redisTemplate.keys(key);
        return new R<>(redisTemplate.delete(keys));
    }

    public void scan(String pattern, Consumer<byte[]> consumer) {
        this.redisTemplate.execute((RedisConnection connection) -> {
            try (Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().count(Long.MAX_VALUE).match(pattern).build())) {
                cursor.forEachRemaining(consumer);
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }

}
