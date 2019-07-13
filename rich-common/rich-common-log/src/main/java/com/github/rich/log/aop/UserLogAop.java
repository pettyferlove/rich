package com.github.rich.log.aop;

import com.github.rich.common.core.utils.CommonUtils;
import com.github.rich.log.annotation.UserLog;
import com.github.rich.log.dto.OperateLogInfo;
import com.github.rich.log.service.OperateLogService;
import com.github.rich.security.utils.SecurityUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author Petty
 */
@Aspect
@Order(1)
@Component
@Slf4j
public class UserLogAop {

    private final OperateLogService operateLogService;

    public UserLogAop(OperateLogService operateLogService) {
        this.operateLogService = operateLogService;
    }

    @Pointcut("@annotation(com.github.rich.log.annotation.UserLog)")
    public void pointCut() {
    }

    @Around(value = "pointCut()&&@annotation(userLog)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint pjp, UserLog userLog) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        String ip = CommonUtils.getIpAdrress(request);
        String userAgent = request.getHeader("user-agent");
        String method = request.getMethod();
        OperateLogInfo operateLogInfo = new OperateLogInfo();
        operateLogInfo.setDescription(userLog.description());
        operateLogInfo.setOperateType(userLog.type().getValue());
        operateLogInfo.setUserId(SecurityUtil.getUser() == null ? "anonymousUser" : SecurityUtil.getUser().getUserId());
        operateLogInfo.setClientId(SecurityUtil.getOAuth2Request() == null ? "anonymousClient" : SecurityUtil.getOAuth2Request().getClientId());
        operateLogInfo.setOperateTime(LocalDateTime.now());
        operateLogInfo.setRequestIp(ip);
        operateLogInfo.setRequestMethod(method);
        operateLogInfo.setUserAgent(userAgent);
        operateLogService.sendLog(operateLogInfo);
        return pjp.proceed();
    }

}