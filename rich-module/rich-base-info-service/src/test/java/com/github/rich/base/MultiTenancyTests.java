package com.github.rich.base;

import cn.hutool.core.util.IdUtil;
import com.github.rich.base.entity.SystemRole;
import com.github.rich.base.mapper.SystemRoleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultiTenancyTests {

    @Autowired
    private SystemRoleMapper systemRoleMapper;


    @Test
    public void roleInsert(){
        SystemRole systemRole = new SystemRole();
        systemRole.setId(IdUtil.simpleUUID());
        systemRole.setRole("demo");
        systemRole.setRoleName("demo");
        systemRole.setDescription("demo");
        systemRoleMapper.insert(systemRole);

    }

}
