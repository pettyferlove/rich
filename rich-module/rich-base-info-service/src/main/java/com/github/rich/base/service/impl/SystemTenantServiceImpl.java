package com.github.rich.base.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.base.entity.SystemTenant;
import com.github.rich.base.mapper.SystemTenantMapper;
import com.github.rich.base.service.ISystemTenantService;
import com.github.rich.common.core.exception.BaseRuntimeException;
import com.github.rich.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 租户信息 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2019-07-13
 */
@Service
public class SystemTenantServiceImpl extends ServiceImpl<SystemTenantMapper, SystemTenant> implements ISystemTenantService {

    @Override
    public IPage<SystemTenant> page(SystemTenant systemTenant, Page<SystemTenant> page) {
        return this.page(page, Wrappers.lambdaQuery(systemTenant).orderByDesc(SystemTenant::getCreateTime));
    }

    @Override
    public SystemTenant get(String id) {
        return this.getById(id);
    }

    @Override
    public Boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public String create(SystemTenant systemTenant) {
        String systemTenantId = IdUtil.simpleUUID();
        systemTenant.setId(systemTenantId);
        systemTenant.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getUserId());
        systemTenant.setCreateTime(LocalDateTime.now());
        if (this.save(systemTenant)) {
            return systemTenantId;
        } else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    public Boolean update(SystemTenant systemTenant) {
        systemTenant.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getUserId());
        systemTenant.setModifierTime(LocalDateTime.now());
        return this.updateById(systemTenant);
    }

    @Override
    public Boolean changeStatus(SystemTenant tenant) {
        boolean result;
        Integer status = tenant.getStatus();
        tenant.setStatus(status == 0 ? 1 : 0);
        tenant.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getUserId());
        tenant.setModifierTime(LocalDateTime.now());
        result = this.updateById(tenant);
        return result;
    }

    @Override
    public Boolean check(String tenantId) {
        return ObjectUtil.isNotNull(this.getOne(Wrappers.<SystemTenant>lambdaQuery().eq(SystemTenant::getTenantId, tenantId)));
    }

    @Override
    public Boolean checkTenantStatus(String tenantId) {
        SystemTenant tenant = this.getOne(Wrappers.<SystemTenant>lambdaQuery().eq(SystemTenant::getTenantId, tenantId));
        if(ObjectUtil.isNotNull(tenant)){
            return false;
        }else {
            return tenant.getStatus() == 1;
        }
    }

}
