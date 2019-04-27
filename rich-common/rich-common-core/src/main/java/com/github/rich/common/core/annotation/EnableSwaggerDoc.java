package com.github.rich.common.core.annotation;

import com.github.rich.common.core.config.Swagger2Config;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(Swagger2Config.class)
public @interface EnableSwaggerDoc {
}
