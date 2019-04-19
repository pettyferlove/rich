package com.github.rich.base.feign.fallback;

import com.github.rich.base.dto.User;
import com.github.rich.base.feign.RemoteRoleService;
import com.github.rich.base.feign.RemoteUserService;
import com.github.rich.common.core.model.R;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Petty
 */
@Slf4j
@Component
public class RemoteRoleServiceFallbackImpl implements RemoteRoleService {

    @Setter
    private Throwable cause;

    @Override
    public R<List> find() {
        log.error("Feign---RemoteRoleService->find Hystrix Fusing->Date:{},Cause:{}", System.currentTimeMillis(), cause);
        return null;
    }
}
