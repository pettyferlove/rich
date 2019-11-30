package com.github.rich.auth.token;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.security.oauth2.provider.token.store.redis.StandardStringSerializationStrategy;

/**
 * Token储存自定义序列化
 * @author Petty
 */
public class JsonSerializationStrategy extends StandardStringSerializationStrategy {

    private static final GenericJackson2JsonRedisSerializer JSON_SERIALIZER = new GenericJackson2JsonRedisSerializer();

    @Override
    protected <T> T deserializeInternal(byte[] bytes, Class<T> clazz) {
        return JSON_SERIALIZER.deserialize(bytes, clazz);
    }


    @Override
    protected byte[] serializeInternal(Object object) {
        return JSON_SERIALIZER.serialize(object);
    }
}
