package com.github.rich.base.feign;

import com.github.rich.base.feign.factory.RemoteRoleServiceFallbackFactory;
import com.github.rich.common.core.model.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "rich-base-info-service", fallbackFactory = RemoteRoleServiceFallbackFactory.class)
public interface RemoteRoleService {

    @GetMapping("/role/find")
    R<List> find();
}
