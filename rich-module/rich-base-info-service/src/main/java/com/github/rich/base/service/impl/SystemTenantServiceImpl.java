package com.github.rich.base.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.base.constants.CacheConstant;
import com.github.rich.base.entity.SystemTenant;
import com.github.rich.base.mapper.SystemTenantMapper;
import com.github.rich.base.service.ISystemTenantService;
import com.github.rich.base.vo.TenantVO;
import com.github.rich.common.core.exception.BaseRuntimeException;
import com.github.rich.common.core.utils.ConverterUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<TenantVO> all() {
        List<SystemTenant> systemTenants = this.list(Wrappers.<SystemTenant>lambdaQuery().orderByDesc(SystemTenant::getCreateTime));
        Optional<List<TenantVO>> optionalTenants = Optional.ofNullable(ConverterUtil.convertList(SystemTenant.class, TenantVO.class, systemTenants));
        return optionalTenants.orElseGet(ArrayList::new);
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
    public String create(String userId, SystemTenant systemTenant) {
        String systemTenantId = IdUtil.simpleUUID();
        systemTenant.setId(systemTenantId);
        systemTenant.setCreator(userId);
        systemTenant.setCreateTime(LocalDateTime.now());
        if (this.save(systemTenant)) {
            return systemTenantId;
        } else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    public Boolean update(String userId, SystemTenant systemTenant) {
        systemTenant.setModifier(userId);
        systemTenant.setModifyTime(LocalDateTime.now());
        return this.updateById(systemTenant);
    }

    @Override
    @CacheEvict(value = CacheConstant.INNER_API_PREFIX + "base-api-user", allEntries = true)
    public Boolean changeStatus(String userId, SystemTenant tenant) {
        boolean result;
        Integer status = tenant.getStatus();
        tenant.setStatus(status == 0 ? 1 : 0);
        tenant.setModifier(userId);
        tenant.setModifyTime(LocalDateTime.now());
        result = this.updateById(tenant);
        return result;
    }

    @Override
    public Boolean check(String tenantId) {
        return ObjectUtil.isNotNull(this.getOne(Wrappers.<SystemTenant>lambdaQuery().eq(SystemTenant::getTenantId, tenantId)));
    }

    /**
     * 状态变更同时清理掉用户缓存
     *
     * @param tenantId tenantId
     * @return True 有效 False 无效
     */
    @Override
    public Boolean checkTenantStatus(String tenantId) {
        SystemTenant tenant = this.getOne(Wrappers.<SystemTenant>lambdaQuery().eq(SystemTenant::getTenantId, tenantId));
        if (ObjectUtil.isNull(tenant)) {
            return false;
        } else {
            return tenant.getStatus() == 1;
        }
    }

}
