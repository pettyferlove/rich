package com.github.rich.common.core.aop;

import com.github.rich.common.core.constants.CommonConstant;
import com.github.rich.common.core.exception.BaseRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Objects;


/**
 * 通过Aop对Controller返回异常进行包装
 *
 * @author Petty
 * @date 2018年3月1日
 */
@Aspect
@Order(0)
@Component
@Slf4j
public class ControllerAop {

    @Pointcut("execution(public com.github.rich.common.core.vo.R *(..)) && !@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    public void pointCut() {
    }

    /**
     * 处理切点
     *
     * @param pjp ProceedingJoinPoint
     * @return Object
     */
    @Around("pointCut()")
    public Object methodAroundHandler(ProceedingJoinPoint pjp) throws Throwable {
        return methodHandler(pjp);
    }

    private Object methodHandler(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.info("HEADER : name:" + headerName + ", value:" + request.getHeader(headerName));
        }
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("CLASS_METHOD : " + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
        log.info("ARGS : " + Arrays.toString(pjp.getArgs()));
        try {
            log.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
            return pjp.proceed();
        } catch (Throwable e) {
            log.error("Error：", e);
            if (e instanceof AccessDeniedException) {
                throw new BaseRuntimeException(e.getMessage(), CommonConstant.FORBIDDEN);
            } else {
                throw e;
            }
        }
    }
}
