package com.github.rich.common.core.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础上下文管理器
 *
 * @author Petty
 */
public class BaseContextHandler {
    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(32);
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(32);
            threadLocal.set(map);
        }
        return map.get(key);
    }

    /**
     * 清空ThreadLocal，防止内存泄漏
     */
    public static void remove() {
        threadLocal.remove();
    }
}
