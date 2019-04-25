package com.github.rich.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.rich.auth.model.WeChatAuthCallback;
import com.github.rich.auth.service.WeChatAuthService;
import com.github.rich.common.core.config.ThirdPartyPlatformPropertiesConfig;
import com.github.rich.common.core.exception.BaseRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Petty
 */
@Slf4j
@Service
public class WeChatAuthServiceImpl implements WeChatAuthService {

    private final String ERRCODE_KEY = "errcode";
    private final String OPENID_KEY = "openid";
    private final String UNIONID_KEY = "unionid";
    private final String ERRMSG_KEY = "errmsg";

    private final RestTemplate restTemplate;

    private final ThirdPartyPlatformPropertiesConfig properties;

    public WeChatAuthServiceImpl(RestTemplate remoteRestTemplate, ThirdPartyPlatformPropertiesConfig properties) {
        this.restTemplate = remoteRestTemplate;
        this.properties = properties;
    }

    @Override
    public WeChatAuthCallback auth(String jsCode) throws BaseRuntimeException {
        WeChatAuthCallback callback = new WeChatAuthCallback();
        Map<String, String> params = new HashMap<>(4);
        params.put("appid", properties.getWechat().getAppId());
        params.put("secret", properties.getWechat().getAppSecret());
        params.put("jsCode", jsCode);
        params.put("grantType", "authorization_code");
        String authUrl = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={jsCode}&grant_type={grantType}";
        ResponseEntity<String> entity = restTemplate.getForEntity(authUrl, String.class, params);
        JSONObject jsonObject = JSONObject.parseObject(entity.getBody());
        if(!jsonObject.containsKey(ERRCODE_KEY)){
            if(!jsonObject.containsKey(OPENID_KEY)){
                callback.setOpenid(jsonObject.getString(OPENID_KEY));
            }
            if(!jsonObject.containsKey(UNIONID_KEY)){
                callback.setUnionid(jsonObject.getString(UNIONID_KEY));
            }
        }else{
            int errcode = jsonObject.getInteger(ERRCODE_KEY);
            String errmsg = jsonObject.getString(ERRMSG_KEY);
            log.error("WeChatAuthService->auth ERROR:code{},msg:{}",errcode,errmsg);
            throw new OAuth2Exception(errmsg);
        }
        return callback;
    }
}
