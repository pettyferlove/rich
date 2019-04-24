package com.github.rich.security.service;

/**
 * @author Petty
 */
public interface PermissionService {

    /**
     * 判断是否有权限
     * @param permission 资源标识
     * @return Boolean
     */
    boolean hasPermission(String permission);

}
