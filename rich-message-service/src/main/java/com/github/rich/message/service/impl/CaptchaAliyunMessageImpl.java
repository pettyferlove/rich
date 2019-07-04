package com.github.rich.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.github.rich.common.core.exception.BaseRuntimeException;
import com.github.rich.message.config.SmsAliyunProperties;
import com.github.rich.message.dto.message.CaptchaMessage;
import com.github.rich.message.service.ISmsService;
import org.springframework.stereotype.Service;


/**
 * @author Petty
 */
@Service
public class CaptchaAliyunMessageImpl implements ISmsService<CaptchaMessage> {


    private final SmsAliyunProperties aliyunProperties;

    public CaptchaAliyunMessageImpl(SmsAliyunProperties aliyunProperties) {
        this.aliyunProperties = aliyunProperties;
    }

    @Override
    public boolean send(CaptchaMessage message) {
        DefaultProfile profile = DefaultProfile.getProfile("default", aliyunProperties.getAccessKeyId(), aliyunProperties.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        JSONObject params = new JSONObject();
        params.put("code",message.getCaptchaCode());
        request.putQueryParameter("TemplateParam", params.toJSONString());
        request.putQueryParameter("TemplateCode", "SMS_169870184");
        request.putQueryParameter("SignName", aliyunProperties.getSignName());
        request.putQueryParameter("PhoneNumbers", message.getReceiver());
        try {
            CommonResponse response = client.getCommonResponse(request);
            JSONObject result = JSON.parseObject(response.getData());
            if("OK".equals(result.getString("Code"))){
                return true;
            }else{
                throw new BaseRuntimeException("sms send fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseRuntimeException(e);
        }
    }
}
