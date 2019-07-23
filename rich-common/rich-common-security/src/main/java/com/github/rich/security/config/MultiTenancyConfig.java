package com.github.rich.security.config;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.github.rich.common.core.config.MultiTenancyProperties;
import com.github.rich.common.core.exception.BaseRuntimeException;
import com.github.rich.security.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;

/**
 * @author Petty
 */
@Slf4j
@Configuration
@ConditionalOnMissingBean(MultiTenancyProperties.class)
public class MultiTenancyConfig {

    private final MultiTenancyProperties multiTenancyProperties;

    public MultiTenancyConfig(MultiTenancyProperties multiTenancyProperties) {
        this.multiTenancyProperties = multiTenancyProperties;
    }

    @Bean
    public TenantSqlParser tenantSqlParser() {
        return new TenantSqlParser()
                .setTenantHandler(new TenantHandler() {

                    @Override
                    public Expression getTenantId() {
                        // 从当前系统上下文中取出当前请求的服务商ID，通过解析器注入到SQL中。
                        String tenantId = Objects.requireNonNull(SecurityUtil.getUser(), "multi_tenancy #50003 get user error.").getTenantId();
                        if (null == tenantId) {
                            throw new BaseRuntimeException("multi_tenancy #50001 get tenant_id error.");
                        }
                        return new StringValue(tenantId);
                    }

                    @Override
                    public String getTenantIdColumn() {
                        return multiTenancyProperties.getTableField();
                    }

                    @Override
                    public boolean doTableFilter(String tableName) {
                        /*
                        匿名用户无法拿到租户ID，忽略处理
                         */
                        if (ObjectUtil.isNull(SecurityUtil.getUser())) {
                            if (log.isDebugEnabled()) {
                                log.debug("multi_tenancy #50004 ignore tenant control");
                            }
                            return true;
                        }
                        List<String> ignoreRoles = multiTenancyProperties.getIgnore().getRoles();
                        List<String> roles = SecurityUtil.getRoles();
                        if(ignoreRoles.stream().anyMatch(roles::contains)){
                           return ignoreRoles.stream().anyMatch(roles::contains);
                        }
                        return  multiTenancyProperties.getIgnore().getTables().stream().anyMatch((e) -> e.equalsIgnoreCase(tableName));
                    }
                });
    }

}
