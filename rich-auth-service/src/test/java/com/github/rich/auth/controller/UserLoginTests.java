package com.github.rich.auth.controller;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.rich.base.dto.User;
import com.github.rich.base.feign.UserServiceFeignClient;
import com.github.rich.security.service.AbstractCaptchaValidateService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

/**
 * @author Petty
 */
@ActiveProfiles("test")
@SpringBootTest
@Slf4j
public class UserLoginTests {

    private MockMvc mockMvc;

    @MockBean
    private UserServiceFeignClient userServiceFeignClient;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    @Qualifier("smsCaptchaValidateService")
    private AbstractCaptchaValidateService smsCaptchaValidateService;


    static User user;

    @BeforeAll
    static void init() {
        /*初始化用户信息数据*/
        user = new User();
        user.setId("1");
        user.setLoginName("test");
        user.setPassword("{hG3alSLfgIMABKH8A+xg7N+YmikNn3TB1hWx1hddoL4=}926fcfe088220ac4b8918764ae9feca07020d8d53b78072b7d3f43b1a29b3c30");
        user.setUserName("test");
        user.setStatus(1);
    }

    @BeforeEach
    public void beforeEach() {
        log.info("测试开始");
        mockMvc =
                //配置ServletContext上下文
                MockMvcBuilders.webAppContextSetup(webApplicationContext)
                        //设置springSecurity配置
                        .apply(springSecurity())
                        .build();
    }

    @AfterEach
    void tearDown() {
        log.info("测试结束");
    }


    /**
     * 测试用户通过用户名登录
     *
     * @throws Exception exception
     */
    @Test
    public void testUserLoginByLoginName() throws Exception {
        when(userServiceFeignClient.findUserByLoginName(any(String.class))).thenReturn(user);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/oauth/token")
                .param("username", "rich")
                .param("password", "123456")
                .param("grant_type", "password")
                .param("client_id", "rich")
                .param("client_secret", "rich@123456")
        );
        resultActions.andDo(new ResultHandler() {
            @Override
            public void handle(MvcResult result) throws Exception {
                String content = result.getResponse().getContentAsString();
                JSONObject json = JSON.parseObject(content);
                assertThat(json.containsKey("access_token")).isTrue();
                assertThat(json.containsKey("token_type")).isTrue();
                assertThat(json.containsKey("refresh_token")).isTrue();
                assertThat(json.containsKey("expires_in")).isTrue();
                assertThat(json.containsKey("scope")).isTrue();
            }
        });
    }


    /**
     * 测试用户通过手机号码和验证码登录
     * @throws Exception exception
     */
    @Test
    public void testUserLoginByUserByMobile() throws Exception {
        when(userServiceFeignClient.findUserByMobile(any(String.class))).thenReturn(user);
        /*模拟Service默认返回为True*/
        when(smsCaptchaValidateService.validate(any(String.class),any(String.class))).thenReturn(true);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/oauth/token")
                .param("mobile", "13094186549")
                .param("sms_code", "123456")
                .param("grant_type", "mobile")
                .param("client_id", "rich")
                .param("client_secret", "rich@123456")
        );
        resultActions.andDo(new ResultHandler() {
            @Override
            public void handle(MvcResult result) throws Exception {
                String content = result.getResponse().getContentAsString();
                JSONObject json = JSON.parseObject(content);
                assertThat(json.containsKey("access_token")).isTrue();
                assertThat(json.containsKey("token_type")).isTrue();
                assertThat(json.containsKey("refresh_token")).isTrue();
                assertThat(json.containsKey("expires_in")).isTrue();
                assertThat(json.containsKey("scope")).isTrue();
            }
        });
    }

    /**
     * 测试用户通过微信登录---绑定OpenID的情况
     * @throws Exception exception
     */
    @Test
    public void testUserLoginByWeChatOpenId() throws Exception {
        when(userServiceFeignClient.findByWeChatOpenId(any(String.class))).thenReturn(user);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/oauth/token")
                .param("open_id", UUID.fastUUID().toString())
                .param("grant_type", "wechat")
                .param("client_id", "rich")
                .param("client_secret", "rich@123456")
        );
        resultActions.andDo(new ResultHandler() {
            @Override
            public void handle(MvcResult result) throws Exception {
                String content = result.getResponse().getContentAsString();
                JSONObject json = JSON.parseObject(content);
                assertThat(json.containsKey("access_token")).isTrue();
                assertThat(json.containsKey("token_type")).isTrue();
                assertThat(json.containsKey("refresh_token")).isTrue();
                assertThat(json.containsKey("expires_in")).isTrue();
                assertThat(json.containsKey("scope")).isTrue();
            }
        });
    }

    /**
     * 测试用户通过微信登录---绑定UnionID的情况
     * @throws Exception exception
     */
    @Test
    public void testUserLoginByWeChatUnionId() throws Exception {
        when(userServiceFeignClient.findByWeChatUnionId(any(String.class))).thenReturn(user);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/oauth/token")
                .param("union_id", UUID.fastUUID().toString())
                .param("grant_type", "wechat")
                .param("client_id", "rich")
                .param("client_secret", "rich@123456")
        );
        resultActions.andDo(new ResultHandler() {
            @Override
            public void handle(MvcResult result) throws Exception {
                String content = result.getResponse().getContentAsString();
                JSONObject json = JSON.parseObject(content);
                assertThat(json.containsKey("access_token")).isTrue();
                assertThat(json.containsKey("token_type")).isTrue();
                assertThat(json.containsKey("refresh_token")).isTrue();
                assertThat(json.containsKey("expires_in")).isTrue();
                assertThat(json.containsKey("scope")).isTrue();
            }
        });
    }


}
