package com.github.rich.base.feign;

import com.github.rich.base.domain.dto.OAuthClientDetails;
import com.github.rich.base.feign.factory.ClientServiceFeignClientFallbackFactory;
import com.github.rich.common.core.constants.CommonConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Petty
 */
@FeignClient(contextId = "clientServiceFeignClient", name = "rich-base-info-service", fallbackFactory = ClientServiceFeignClientFallbackFactory.class)
public interface ClientServiceFeignClient {

    /**
     * 获取客户端信息
     *
     * @param clientId 客户端ID
     * @return OAuthClientDetails
     */
    @GetMapping(CommonConstant.INNER_SERVICE_PREFIX + "/oauth/client/{clientId}")
    OAuthClientDetails loadClientByClientId(@PathVariable String clientId);

}
