package com.github.rich.base;

import com.alibaba.fastjson.JSON;
import com.github.rich.base.service.ISystemMenuResourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuServiceTests {

    @Autowired
    private ISystemMenuResourceService systemMenuResourceService;

    @Test
    public void buildTree(){
        System.out.println(JSON.toJSONString(systemMenuResourceService.loadTree()));
    }

}
