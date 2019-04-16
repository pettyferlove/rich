package com.github.rich.common.core.bean.interceptor;

import okhttp3.Interceptor;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Feign请求拦截器
 *
 * @author Petty
 * @date 2018/4/25
 */
@Component
public class ServiceFeignInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request());
    }
}
