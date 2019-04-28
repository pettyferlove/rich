package com.github.rich.thirdparty.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信jscode2session 回调信息
 * @author Petty
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeChatCode2SessionCallback implements Serializable {

    private static final long serialVersionUID = -3494595764100365549L;
    /**
     * 基本开放认证id
     */
    String openId;

    /**
     * 需要公司认证才可以获取，该公司主体下属所有应用均可使用
     */
    String unionId;

    /**
     * SessionKey 与微信服务器之间维持的Session Key
     */
    String sessionKey;
}
