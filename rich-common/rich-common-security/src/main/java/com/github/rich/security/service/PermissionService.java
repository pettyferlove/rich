package com.github.rich.security.service;

/**
 * 只判断资源标识，如果需要通过角色去判断，直接使用自带的EL表达式
 * eg: @PreAuthorize("hasRole('ADMIN')")
 *
 * @author Petty
 */
public interface PermissionService {

    /**
     * 判断是否有权限
     *
     * @param permission 资源标识
     * @return Boolean
     */
    boolean hasPermission(String permission);

}
