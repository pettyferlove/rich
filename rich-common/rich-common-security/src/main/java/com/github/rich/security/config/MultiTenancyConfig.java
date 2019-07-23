package com.github.rich.security.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.github.rich.common.core.config.MultiTenancyProperties;
import com.github.rich.common.core.exception.BaseRuntimeException;
import com.github.rich.security.utils.SecurityUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import net.sf.jsqlparser.expression.Expression;

import java.util.List;
import java.util.Objects;

/**
 * @author Petty
 */
@Slf4j
@Configuration
@ConditionalOnMissingBean(MultiTenancyProperties.class)
public class MultiTenancyConfig {

    /**
     * 需要忽略的表名
     */
    private static final List<String> IGNORE_TENANT_TABLES = Lists.newArrayList("system_tenant", "system_user_extend");

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
                          匿名或无需鉴权的请求将忽略租户控制
                         */
                        List<String> roles = multiTenancyProperties.getIgnore().getRoles();
                        SecurityUtil.getRoles();
                        if (ObjectUtil.isNull(SecurityUtil.getUser())) {
                            if (log.isDebugEnabled()) {
                                log.debug("multi_tenancy #50004 ignore tenant control");
                            }
                            return true;
                        } else {
                            /*
                              忽略TenantID为空时的租户控制
                             */
                            String tenantId = SecurityUtil.getUser().getTenantId();
                            if (StrUtil.isEmpty(tenantId)) {
                                log.debug("multi_tenancy #50004 ignore tenant control");
                                return true;
                            }
                        }
                        return IGNORE_TENANT_TABLES.stream().anyMatch((e) -> e.equalsIgnoreCase(tableName));
                    }
                });
    }

}
