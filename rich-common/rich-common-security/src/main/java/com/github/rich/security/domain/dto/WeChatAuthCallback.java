package com.github.rich.security.domain.dto;

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
public class WeChatAuthCallback implements Serializable {

    private static final long serialVersionUID = 2745805794408442721L;
    /**
     * 基本开放认证id
     */
    String openid;

    /**
     * 需要公司认证才可以获取，该公司主体下属所有应用均可使用
     */
    String unionid;
}
