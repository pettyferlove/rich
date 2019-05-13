package com.github.rich.common.core.web;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 缓存管理接口
 * 由各个服务实现具体逻辑
 * @author Petty
 */
@RequestMapping("/cache/manager")
public interface CacheManagerController {
}
