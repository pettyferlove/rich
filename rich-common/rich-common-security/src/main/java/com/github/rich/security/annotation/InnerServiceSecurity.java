package com.github.rich.security.annotation;

import java.lang.annotation.*;

/**
 * 内部服务安全检测
 * 使用该注解则会在Feign 远程调用的时候为请求加上Token
 * @author Petty
 * @see com.github.rich.security.aop.ServiceSecurityAop
 * @see com.github.rich.security.feign.RichFeignClientInterceptor
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerServiceSecurity {
}
