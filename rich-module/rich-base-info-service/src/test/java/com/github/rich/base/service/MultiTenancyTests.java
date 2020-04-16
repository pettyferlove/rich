package com.github.rich.base.service;

import cn.hutool.core.util.IdUtil;
import com.github.rich.base.entity.SystemRole;
import com.github.rich.base.mapper.SystemRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class MultiTenancyTests {

    @Autowired
    private SystemRoleMapper systemRoleMapper;


    @Test
    public void roleInsert(){
        SystemRole systemRole = new SystemRole();
        systemRole.setRole("demo");
        systemRole.setRoleName("demo");
        systemRole.setDescription("demo");
        systemRoleMapper.insert(systemRole);

    }

}
