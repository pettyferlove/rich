package com.github.rich.security.annotation;

import com.github.rich.security.component.RichResourceServerAutoConfiguration;
import com.github.rich.security.component.RichSecurityBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

@Documented
@Inherited
@EnableResourceServer
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({RichResourceServerAutoConfiguration.class, RichSecurityBeanDefinitionRegistrar.class})
public @interface EnableRichResourceServer {
}
