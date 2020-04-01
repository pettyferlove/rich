package com.github.rich.auth;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ActiveProfiles("dev")
@SpringBootTest
public class RichAuthServiceApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void beforeTest(){
        mockMvc =
                //配置ServletContext上下文
                MockMvcBuilders.webAppContextSetup(webApplicationContext)
                        //设置springSecurity配置
                        .apply(springSecurity())
                        .build();
    }


    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(authenticated());
    }


}
