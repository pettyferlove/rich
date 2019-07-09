package com.github.rich.auth.endpoint;

import com.alibaba.fastjson.JSONObject;
import com.github.rich.auth.service.CaptchaValidateService;
import com.github.rich.common.core.utils.SMSUtil;
import com.github.rich.common.core.constants.SecurityConstant;
import com.github.rich.common.core.vo.R;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码生成
 *
 * @author Petty
 */
@Slf4j
@FrameworkEndpoint
@RequestMapping("captcha")
public class CaptchaEndpoint {

    private final CaptchaValidateService defaultCaptchaValidateService;
    private final CaptchaValidateService smsCaptchaValidateService;
    private final DefaultKaptcha captchaProducer;

    public CaptchaEndpoint(@Qualifier("defaultCaptchaValidateService") CaptchaValidateService defaultCaptchaValidateService, @Qualifier("smsCaptchaValidateService") CaptchaValidateService smsCaptchaValidateService, DefaultKaptcha captchaProducer) {
        this.defaultCaptchaValidateService = defaultCaptchaValidateService;
        this.smsCaptchaValidateService = smsCaptchaValidateService;
        this.captchaProducer = captchaProducer;
    }

    @RequestMapping("/create/{machineCode}")
    public void createCode(@PathVariable String machineCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String randomStr = captchaProducer.createText();
        BufferedImage image = captchaProducer.createImage(randomStr);
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "JPEG", out);
        defaultCaptchaValidateService.create(machineCode, randomStr, SecurityConstant.VALIDATE_CODE_EXPIRY);
        out.flush();
        out.close();
    }

    @RequestMapping("/mobile/{mobileTel}")
    public void createMobileCaptcha(@PathVariable String mobileTel, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String randomStr = SMSUtil.createRandomVcode();
        log.info("Captcha -> Mobile:{},{}",mobileTel,randomStr);
        smsCaptchaValidateService.create(mobileTel,randomStr, SecurityConstant.SMS_VALIDATE_CODE_EXPIRY);
        try {
            response.reset();
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(JSONObject.toJSON(new R<>()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
