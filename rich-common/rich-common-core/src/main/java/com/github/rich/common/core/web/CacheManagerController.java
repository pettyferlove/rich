package com.github.rich.common.core.web;

import com.github.rich.common.core.model.R;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

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
     * @return 删除个数
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/clear/all")
    public R<Long> clearAll(){
        Set<String> keys = redisTemplate.keys("*");
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
        Set<String> keys = redisTemplate.keys(key);
        return new R<>(redisTemplate.delete(keys));
    }

}
