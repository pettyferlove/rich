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

/**
 * @author Petty
 */
public abstract class AbstractAliyunMessageImpl implements ISmsService<CaptchaMessage> {

    private final SmsAliyunProperties aliyunProperties;

    AbstractAliyunMessageImpl(SmsAliyunProperties aliyunProperties) {
        this.aliyunProperties = aliyunProperties;
    }

    @Override
    public abstract boolean send(CaptchaMessage message);

    /**
     * 针对阿里云封装的发送方法
     * @param message 消息实体
     * @param templateCode 模板ID
     * @return boolean
     */
    public  boolean sendSms(CaptchaMessage message, String templateCode){
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
        request.putQueryParameter("TemplateCode", templateCode);
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
