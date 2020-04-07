package com.github.rich.security.test;


import com.github.rich.common.core.constants.CommonConstant;
import org.springframework.core.annotation.AliasFor;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.*;

/**
 * @author Petty
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = WithMockRichAuthUserSecurityContextFactory.class)
public @interface WithMockRichAuthUser {
    /**
     * 用户ID
     * @return String
     */
    String id() default "anonymous#default_id";

    /**
     * 默认用户名
     * @return String
     */
    String value() default "user";

    /**
     * 用户登录名
     * @return String
     */
    String loginName() default "";

    /**
     * 用户状态
     * @return
     */
    int status() default CommonConstant.STATUS_NORMAL;

    /**
     * 用户登录名
     * @return
     */
    String username() default "anonymous";

    /**
     * 用户角色
     * @return String[]
     */
    String[] roles() default { "USER" };

    /**
     * 用户权限
     * @return String[]
     */
    String[] permissions() default {};

    /**
     * 用户密码
     * @return String
     */
    String password() default "password";

    @AliasFor(annotation = WithSecurityContext.class)
    TestExecutionEvent setupBefore() default TestExecutionEvent.TEST_METHOD;
}
