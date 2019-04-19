package com.github.rich.security.aop;

import com.github.rich.common.core.context.BaseContextHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Petty
 */
@Aspect
@Order(11)
@Component
@Slf4j
public class ServiceSecurityAop {


    @Pointcut("@annotation(com.github.rich.security.annotation.InnerServiceSecurity)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object methodAroundHandler(ProceedingJoinPoint pjp) {
        Object result = null;
        try {
            BaseContextHandler.set("x-inner-service-security",true);
            result = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
}
