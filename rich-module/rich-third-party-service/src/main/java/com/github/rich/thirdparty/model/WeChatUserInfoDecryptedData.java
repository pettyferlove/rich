package com.github.rich.thirdparty.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信用户信息解密数据
 * @author Petty
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeChatUserInfoDecryptedData implements Serializable {
    private static final long serialVersionUID = -7859800917249430406L;
    String openId;
    String nickName;
    Integer gender;
    String city;
    String province;
    String country;
    String avatarUrl;
    String unionId;
    WeChatWaterMark watermark;
}
